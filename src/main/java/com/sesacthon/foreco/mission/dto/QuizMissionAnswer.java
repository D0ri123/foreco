package com.sesacthon.foreco.mission.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class QuizMissionAnswer {

  @Schema(description = "정답인 선택지의 id")
  private final Long id;
  @Schema(description = "정답인 선택지의 명칭")
  private final String name;
  @Schema(description = "정답인 이미지의 원본 url")
  private final String imageUrl;
  @Schema(description = "배출 방법")
  private final String disposalMethod;

  public QuizMissionAnswer(Long id, String name, String imageUrl, String disposalMethod) {
    this.id = id;
    this.name = name;
    this.imageUrl = imageUrl;
    this.disposalMethod = disposalMethod;
  }
}
