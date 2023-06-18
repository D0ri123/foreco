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

@Tag(name="AI", description = "AI 이미지 업로드 관련 api")
@RestController
@RequiredArgsConstructor
public class S3Controller {

  private final S3Uploader s3Uploader;

  @Operation(summary = "AI서버 이미지 업로드", description = "촬영 사진을 S3에 업로드하여 이미지 url을 AI 서버로 보낸 후 처리값을 확인할 수 있습니다.")
  @PostMapping(value = "/api/v1/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<DataResponse<UploadDto>> sendTrashImg (
      @RequestPart("img") MultipartFile multipartFile) throws IOException, IOException {
    String fileUrl = s3Uploader.expectedFileUrl(multipartFile);
    UploadDto uploadDto = s3Uploader.sendToAiServer(fileUrl);
    return new ResponseEntity<>(
        DataResponse.of(HttpStatus.CREATED, "촬영 이미지 업로드 성공", uploadDto), HttpStatus.CREATED);
  }

}
