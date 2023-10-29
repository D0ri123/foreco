package com.sesacthon.foreco.mission.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class ImageDivisionRequestDto {
  @Schema(description = "퀴즈 문제로 사용할 이미지 원본 url")
  private final String imageUrl;
  @Schema(description = "이미지속 개체의 좌표")
  private final String coordinate;

  public ImageDivisionRequestDto(String image_url, String coordinate) {
    this.imageUrl = image_url;
    this.coordinate = coordinate;
  }
}
