package com.sesacthon.foreco.mock.mission.dto;

import com.sesacthon.foreco.mission.dto.QuizMissionChoice;
import java.util.List;
import lombok.Getter;

@Getter
public class QuizMissionDto {

  MissionInfo missionInfo;
  List<String> images;
  List<QuizMissionChoice> choices;
  QuizMissionAnswer answer;

  public QuizMissionDto(MissionInfo missionInfo, List<String> images,
      List<QuizMissionChoice> choices,
      QuizMissionAnswer answer) {
    this.missionInfo = missionInfo;
    this.images = images;
    this.choices = choices;
    this.answer = answer;
  }
}
