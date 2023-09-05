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

@Tag(name = "카메라", description = "카메라 + AI 관련 api")
@RestController
@RequiredArgsConstructor
public class S3Controller {

  private final S3Uploader s3Uploader;

  @Operation(summary = "카메라로 쓰레기 분석", description = "촬영 사진을 요청하면 AI 모델의 분석 결과값을 확인할 수 있습니다.")
  @PostMapping(value = "/api/v1/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<DataResponse<UploadDto>> uploadEventImg(
      @RequestPart("img") MultipartFile multipartFile) throws IOException {
    UploadDto uploadDto = s3Uploader.sendToAiServer(multipartFile);
    return new ResponseEntity<>(
        DataResponse.of(HttpStatus.CREATED, "촬영 이미지 업로드 성공", uploadDto), HttpStatus.CREATED);
  }

}
