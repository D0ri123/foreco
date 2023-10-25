package com.sesacthon.foreco.mock.mission.dto;

import lombok.Getter;

@Getter
public class QuizMissionChoice {

  Long id;
  String name;

  public QuizMissionChoice(Long id, String name) {
    this.id = id;
    this.name = name;
  }
}
