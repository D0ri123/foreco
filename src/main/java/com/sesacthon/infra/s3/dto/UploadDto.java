package com.sesacthon.infra.s3.dto;

import lombok.Getter;

@Getter
public class UploadDto {

  private final String message;

  public UploadDto(String message) {
    this.message = message;
  }

}
