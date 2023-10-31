package com.sesacthon.foreco.mission.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class MissionResultInfoDto {

  /**
   * 지급할 포인트
   */
  private final Long rewardPoint;
  /**
   * 미션 수행 포인트가 반영된 이후의 유저가 보유한 포인트
   */
  private final Long userPoint;
}
