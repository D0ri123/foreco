package com.sesacthon.foreco.mission.entity;

import static jakarta.persistence.GenerationType.*;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 미션 관련 정보를 담은 테이블
 */
@Entity
@NoArgsConstructor
@Getter
public class Mission {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  /**
   * 미션 제목
   */
  private String title;

  /**
   * 미션 요청 API url
   */
  private String apiUrl;

  /**
   * 미션 요청 메서드
   */
  private String apiMethod;

  /**
   * 미션 포인트
   */
  private Long rewardPoint;

  /**
   * 미션 난이도
   */
  @Enumerated(EnumType.STRING)
  private Difficulty difficulty;

  /**
   * 미션 참여 가능 횟수
   */
  private Long personalCount;

  /**
   * 미션 총 참여 가능 횟수
   */
  private Long totalCount;

  /**
   * 미션 종류
   */
  @Enumerated(EnumType.STRING)
  private Kind kind;

  /**
   * 미션 설명
   */
  private String description;

}
