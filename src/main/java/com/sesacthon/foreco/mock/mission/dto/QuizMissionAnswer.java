package com.sesacthon.foreco.mock.mission.dto;

import lombok.Getter;

@Getter
public class QuizMissionAnswer {

  Long id;
  String name;
  String imageUrl;
  String disposalMethod;

  public QuizMissionAnswer(Long id, String name, String imageUrl, String disposalMethod) {
    this.id = id;
    this.name = name;
    this.imageUrl = imageUrl;
    this.disposalMethod = disposalMethod;
  }
}
