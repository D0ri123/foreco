package com.sesacthon.foreco.mock.mission.dto;

import com.sesacthon.foreco.mission.entity.Difficulty;
import com.sesacthon.foreco.mission.entity.Kind;
import com.sesacthon.foreco.mission.entity.Mission;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class MissionDetailDto {

  @Schema(description = "미션 Id")
  private final Long id;

  @Schema(description = "미션 종류(WIZ, ETC")
  private final Kind kind;

  @Schema(description = "미션 제목")
  private final String title;

  @Schema(description = "미션 설명")
  private final String description;

  @Schema(description = "미션 난이도")
  private final Difficulty difficulty;

  @Schema(description = "미션 지급 포인트")
  private final Long rewardPoint;

  @Schema(description = "미션 참여 가능 총 횟수")
  private final Long totalCount;

  @Schema(description = "현재까지 사용자들이 참여한 총 횟수")
  private final Long totalNumberOfParticipating;

  @Schema(description = "개인이 해당 미션에 참여한 횟수")
  private final Long personalParticipatingCount;

  @Schema(description = "개인별 미션 참여 가능 총 횟수")
  private final Long personalCount;

  @Schema(description = "미션 아이콘 url")
  private final String iconUrl;

  @Schema(description = "기업 미션 url")
  private final String missionUrl;

  public MissionDetailDto(Mission mission, Long missionSize, Long personalSize, String iconUrl) {
    this.id = mission.getId();
    this.kind = mission.getKind();
    this.title = mission.getTitle();
    this.description = mission.getDescription();
    this.difficulty = mission.getDifficulty();
    this.rewardPoint = mission.getRewardPoint();
    this.totalCount = mission.getTotalCount();
    this.totalNumberOfParticipating = missionSize;
    this.personalCount = mission.getPersonalCount();
    this.personalParticipatingCount = personalSize;
    this.iconUrl = iconUrl;
    this.missionUrl = mission.getMissionLinkUrl();
  }

}
