package com.sesacthon.foreco.mission.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MissionResultInfoDto {

  /**
   * 지급할 포인트
   */
  private Long rewardPoint;
  /**
   * 미션 수행 포인트가 반영된 이후의 유저가 보유한 포인트
   */
  private Long userPoint;
}
