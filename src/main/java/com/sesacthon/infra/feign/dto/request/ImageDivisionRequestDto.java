package com.sesacthon.infra.feign.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class ImageDivisionRequestDto {
  @Schema(description = "퀴즈 문제로 사용할 이미지 원본 url")
  private final String image_url;
  @Schema(description = "이미지속 개체의 좌표")
  private final String coordinate;

  public ImageDivisionRequestDto(String image_url, String coordinate) {
    this.image_url = image_url;
    this.coordinate = coordinate;
  }
}
