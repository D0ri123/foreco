package com.sesacthon.foreco.mission.dto;

import lombok.Getter;

@Getter
public class ImageDivisionRequestDto {
  String image_url;
  String coordinate;

  public ImageDivisionRequestDto(String image_url, String coordinate) {
    this.image_url = image_url;
    this.coordinate = coordinate;
  }
}
