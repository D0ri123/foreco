package com.sesacthon.infra.s3.service;

import static com.sesacthon.global.exception.ErrorCode.HANDLE_ACCESS_DENIED;
import static com.sesacthon.global.exception.ErrorCode.IMAGE_WRONG_FILE_FORMAT;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.sesacthon.foreco.category.entity.Trash;
import com.sesacthon.foreco.category.repository.TrashRepository;
import com.sesacthon.infra.s3.dto.UploadDto;
import com.sesacthon.infra.s3.exception.ImageUploadException;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3Uploader {

  private final AmazonS3Client amazonS3Client;

  private final TrashRepository trashRepository;

  @Value("${cloud.aws.s3.bucket}")
  private String bucket;

  @Value("${ai.modelUrl}")
  private String aiUrl;

  /**
   * @param multipartFile 사용자가 촬영한 사진
   * @return S3에 올린 이후 url을 반환
   */
  private String uploadFile(MultipartFile multipartFile) {
    //multipartFile 을 String 형식 fileName 으로 변환한다.
    String fileName = createFileName(multipartFile.getOriginalFilename());
    uploadToS3(multipartFile, fileName, getObjectMetadata(multipartFile));
    return amazonS3Client.getUrl(bucket, fileName).toString();
  }

  /**
   * @param multipartFile 업로드할 이미지
   * @param prefix        파일이 위치할 경로(폴더)
   * @return 이미지 url
   */
  public String uploadFile(MultipartFile multipartFile, String prefix) {
    //multipartFile 을 String 형식 fileName 으로 변환한다.
    String fileName = prefix + "/" + createFileName(multipartFile.getOriginalFilename());
    uploadToS3(multipartFile, fileName, getObjectMetadata(multipartFile));
    return amazonS3Client.getUrl(bucket, fileName).toString();
  }

  /**
   *
   * @param base64Data base64로 인코딩된 문자열
   * @param prefix s3에 저장할 경로
   * @return s3에 저장된 이미지 url
   */
  public String uploadFileUsingStream(String base64Data, String prefix) {

    byte[] bI = org.apache.commons.codec.binary.Base64.decodeBase64(
        (base64Data.substring(base64Data.indexOf(",") + 1)).getBytes());

    InputStream inputStream = new ByteArrayInputStream(bI);
    String fileName = prefix + "/" + UUID.randomUUID().toString();

    ObjectMetadata objectMetadata = new ObjectMetadata();
    objectMetadata.setContentLength(bI.length);
    objectMetadata.setContentType("image/png");
    try {
      amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
          .withCannedAcl(CannedAccessControlList.PublicRead));
    } catch (Exception e) {
      throw new ImageUploadException(HANDLE_ACCESS_DENIED);
    }
    return amazonS3Client.getUrl(bucket, fileName).toString();
  }


  /**
   * @param fileName multipartFile의 파일 이름
   * @return 'direcotry 이름 + 랜덤 UUID + 확장자' 를 반환
   */
  private String createFileName(String fileName) {
    return UUID.randomUUID().toString().concat(getFileExtension(fileName));
  }


  /**
   * @param fileName createFileName 메서드를 통해서 변경된 파일 이름
   */
  private String getFileExtension(String fileName) {
    List<String> possibleExtensions = Arrays.asList(".jpg", ".png", ".jpeg");
    String extension = fileName.substring(fileName.lastIndexOf("."));
    if (!possibleExtensions.contains(extension)) {
      throw new ImageUploadException(IMAGE_WRONG_FILE_FORMAT);
    }
    return extension;
  }


  /**
   * @param file           MultipartFile
   * @param fileName       createFileName 메서드를 통해서 변경된 파일 이름
   * @param objectMetadata MultipartFile의 length와 contentType을 가진 객체
   */
  private void uploadToS3(MultipartFile file, String fileName, ObjectMetadata objectMetadata) {
    try (InputStream inputStream = file.getInputStream()) {
      amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
          .withCannedAcl(CannedAccessControlList.PublicRead));
    } catch (IOException e) {
      throw new ImageUploadException(HANDLE_ACCESS_DENIED);
    }
  }


  /**
   * @param file MultipartFile
   * @return 메타데이터를 가진 객체를 반환
   */
  private static ObjectMetadata getObjectMetadata(MultipartFile file) {
    ObjectMetadata objectMetadata = new ObjectMetadata();
    objectMetadata.setContentLength(file.getSize());
    objectMetadata.setContentType(file.getContentType());
    return objectMetadata;
  }

  /**
   * @param multipartFile 사용자가 촬영한 이미지 파일
   * @return 서버 전송 결과 메시지 + 분석 결과값 반환
   */
  public UploadDto sendToAiServer(MultipartFile multipartFile) throws IOException {
    final String endPoint = aiUrl;

    String fileUrl = uploadFile(multipartFile);

    URL url = new URL(endPoint);
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

    //생성한 url connection 이 서버에 데이터를 보낼 수 있는지 여부 설정
    connection.setDoOutput(true);
    //url 요청에 대한 메소드를 설정
    connection.setRequestMethod("POST");
    //일반 요청 속성을 지정
    connection.setRequestProperty("Content-Type", "application/json");

    String jsonPayload = "{\"image_url\": \"" + fileUrl + "\"}";

    try (OutputStream outputStream = connection.getOutputStream()) {
      outputStream.write(jsonPayload.getBytes());
    }

    // AI 서버로 요청 전송 및 응답 처리
    int responseCode = connection.getResponseCode();

    Long trashId = -1L;
    if (responseCode == HttpURLConnection.HTTP_OK) {
      // 성공적으로 이미지 전달했을 경우
      BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
      String inputLine;
      StringBuilder response = new StringBuilder();

      while ((inputLine = in.readLine()) != null) {
        response.append(inputLine);
      }
      in.close();

      String resultValue = removeEscape(response.toString());
      //단일 품목이며, 조회가 가능한 품목인 경우 상세조회할 trash의 id를 반환함.
      trashId = judgeItCanBeSearched(resultValue);
      return new UploadDto("AI 서버에 이미지 전송 성공", resultValue, trashId);
    } else {
      // 전달 실패
      return new UploadDto("AI 서버에 이미지 전송 실패", null, trashId);
    }
  }

  private Long judgeItCanBeSearched(String resultValue) {
    //단일 재질인지 판단
    if (resultValue.contains("],[")) {
      return -1L;
    }

    //단일 재질인 경우에도, 조회가 가능한 데이터인지 판단
    int endIndex = resultValue.indexOf(",");
    if (endIndex < 0) {    //ai응답 값부터 확인해봐야함
      return -1L;
    }
    String keyword = resultValue.substring(2, endIndex);
    Optional<Trash> trash = trashRepository.findByNameContainingAndParentTrashIsNotNull(
        keyword);   //keyword를 포함한 name이며, parentTrash가  null이 아닌 경우를 찾음.
    if (trash.isPresent()) {
      return trash.get().getId();
    } else {
      return -1L;
    }
  }

  private String removeEscape(String response) {
    return response.replace("\"", "")
        .replace("bboxes:", "")
        .replace("{", "")
        .replace("}", "");
  }
}

