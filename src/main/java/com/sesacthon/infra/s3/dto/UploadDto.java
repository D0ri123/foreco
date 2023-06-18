package com.sesacthon.infra.s3.dto;

import lombok.Getter;

/**
 * AI 서버 전달 결과 response dto
 */

@Getter
public class UploadDto {

  private final String message;
  private String result;

  public UploadDto(String message, String result) {
    this.message = message;
    this.result = result;
  }

  public UploadDto(String message) {
    this.message = message;
  }

}
