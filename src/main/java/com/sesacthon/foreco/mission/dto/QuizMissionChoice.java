package com.sesacthon.foreco.mission.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class QuizMissionChoice {

  @Schema(description = "선택지의 id")
  private final Long id;
  @Schema(description = "선택지의 이름")
  private final String name;

  public QuizMissionChoice(Long id, String name) {
    this.id = id;
    this.name = name;
  }
}
