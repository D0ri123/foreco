package com.sesacthon.foreco.mission.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Getter;

@Getter
public class QuizMissionDto {
  @Schema(description = "미션에 대한 정보를 담는 필드")
  private final MissionInfo missionInfo;
  @Schema(description = "분할된 이미지 url")
  private final List<String> images;
  @Schema(description = "선택지의 정보들")
  private final List<QuizMissionChoice> choices;
  @Schema(description = "정답에 대한 정보")
  private final QuizMissionAnswer answer;

  public QuizMissionDto(MissionInfo missionInfo, List<String> images,
      List<QuizMissionChoice> choices,
      QuizMissionAnswer answer) {
    this.missionInfo = missionInfo;
    this.images = images;
    this.choices = choices;
    this.answer = answer;
  }
}
