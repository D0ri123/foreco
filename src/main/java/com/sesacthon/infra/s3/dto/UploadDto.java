package com.sesacthon.infra.s3.dto;

import lombok.Getter;

@Getter
public class UploadDto {

  private final String message;
  private final String result;

  public UploadDto(String message, String result) {
    this.message = message;
    this.result = (result == null) ? "인식할 수 없음" : result;
  }

}
