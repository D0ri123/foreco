package com.sesacthon.foreco.mission.dto;

import lombok.Getter;

@Getter
public class MissionInfo {

  Long rewardPoint;
  String title;
  String description;
  Long personalParticipatingCount;
  Long personalCount;

  public MissionInfo(Long rewardPoint, String title, String description,
      Long personalParticipatingCount, Long personalCount) {
    this.rewardPoint = rewardPoint;
    this.title = title;
    this.description = description;
    this.personalParticipatingCount = personalParticipatingCount;
    this.personalCount = personalCount;
  }
}

