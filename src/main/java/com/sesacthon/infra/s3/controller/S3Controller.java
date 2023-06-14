package com.sesacthon.infra.s3.controller;

import com.sesacthon.infra.s3.dto.UploadDto;
import com.sesacthon.infra.s3.service.S3Uploader;
import com.sesacthon.global.response.DataResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name="S3", description = "S3 관련 api")
@RestController
@RequiredArgsConstructor
public class S3Controller {

  private final S3Uploader s3Uploader;

  @Operation(summary = "이미지 업로드", description = "촬영 사진을 S3에 업로드하여 이미지 url로 변환할 수 있습니다.")
  @PostMapping(value = "/api/v1/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<DataResponse<UploadDto>> uploadEventImg (
      @RequestPart("img") MultipartFile multipartFile) throws IOException {
    //1. 올라간 S3의 이미지 url을 반환한다.
    String fileUrl = s3Uploader.expectedFileUrl(multipartFile);

    //2. 반환된 S3의 이미지 url을 AI모델 엔드포인트로 보낸다.
    UploadDto uploadDto = s3Uploader.sendToAiServer(fileUrl);

    //3. AI모델로 이미지 전송 성공
    return new ResponseEntity<>(
        DataResponse.of(HttpStatus.CREATED, "촬영 이미지 업로드 성공", uploadDto), HttpStatus.CREATED);
  }

}
