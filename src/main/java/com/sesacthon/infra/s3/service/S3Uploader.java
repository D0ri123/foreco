package com.sesacthon.infra.s3.service;

import static com.sesacthon.global.exception.ErrorCode.HANDLE_ACCESS_DENIED;
import static com.sesacthon.global.exception.ErrorCode.IMAGE_WRONG_FILE_FORMAT;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.sesacthon.infra.s3.dto.S3Dto;
import com.sesacthon.infra.s3.dto.UploadDto;
import com.sesacthon.infra.s3.exception.ImageUploadException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
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

  @Value("${cloud.aws.s3.bucket}")
  private String bucket;

  /**
   * @param multipartFile S3에 올릴 multipartFile
   * @return S3에 저장된 이미지의 url 반환
   */
  public S3Dto uploadFile(MultipartFile multipartFile) {
    String fileName = createFileName(multipartFile.getOriginalFilename());
    uploadToS3(multipartFile, fileName, getObjectMetadata(multipartFile));
    return new S3Dto(amazonS3Client.getUrl(bucket, fileName).toString());
  }


  /**
   * 업로드한 이미지를 S3에 저장된 url로 반환한다.
   * @param multipartFile
   * @return
   */
  public String expectedFileUrl(MultipartFile multipartFile) {
    String fileName = createFileName(multipartFile.getOriginalFilename());
    uploadToS3(multipartFile, fileName, getObjectMetadata(multipartFile));
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
    List<String> possibleExtensions = Arrays.asList(".jpg",".png",".jpeg");
    String extension = fileName.substring(fileName.lastIndexOf("."));
    if (!possibleExtensions.contains(extension)) {
      throw new ImageUploadException(IMAGE_WRONG_FILE_FORMAT);
    }
    return extension;
  }


  /**
   * @param file MultipartFile
   * @param fileName createFileName 메서드를 통해서 변경된 파일 이름
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
   * s3에 업로드된 이미지의 url을 AI 모델에 보낸다.
   * @param fileUrl
   */
  public UploadDto sendToAiServer(String fileUrl) throws IOException {
    final String endPoint = "http://61.82.87.120:80/upload";
    URL url = new URL(endPoint);

    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

    //생성한 url connection이 서버에 데이터를 보낼 수 있는지 여부 설정
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
    if (responseCode == HttpURLConnection.HTTP_OK) {
      BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
      String inputLine;
      StringBuilder result = new StringBuilder();
      while((inputLine=in.readLine()) != null) {
        result.append(inputLine);
      }
      in.close();
      // 성공적으로 이미지 전달
      return new UploadDto("AI 서버에 이미지 전송 성공", result.toString());
    } else {
      // 전달 실패
      return new UploadDto("AI 서버에 이미지 전송 실패");
    }
  }


}
