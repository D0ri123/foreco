package com.sesacthon.foreco.mission.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;


@Getter
public class MissionResultDto {

  @Schema(description = "리워드를 정립할 미션의 id")
  private Long missionId;
  @Schema(description = "수행한 성공,실패 여부")
  private Boolean isSuccess;
}
