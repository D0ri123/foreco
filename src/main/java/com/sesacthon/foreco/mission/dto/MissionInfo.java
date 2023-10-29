package com.sesacthon.foreco.mission.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class MissionInfo {

  @Schema(description = "미션성공시 지급되는 포인트")
  private final Long rewardPoint;
  @Schema(description = "미션 제목")
  private final String title;
  @Schema(description = "미션 소개글")
  private final String description;
  @Schema(description = "미션 수행을 요청한 유저(맴버)가 참여한 횟수")
  private final Long personalParticipatingCount;
  @Schema(description = "미션별 최대 참여 가능한 횟수")
  private final Long personalCount;

  public MissionInfo(Long rewardPoint, String title, String description,
      Long personalParticipatingCount, Long personalCount) {
    this.rewardPoint = rewardPoint;
    this.title = title;
    this.description = description;
    this.personalParticipatingCount = personalParticipatingCount;
    this.personalCount = personalCount;
  }
}

