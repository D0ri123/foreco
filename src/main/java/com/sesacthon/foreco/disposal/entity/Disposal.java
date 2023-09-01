package com.sesacthon.foreco.disposal.entity;

import com.sesacthon.foreco.category.entity.RegionCategory;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 지역 기반 쓰레기 배출 정보
 */
@Entity
@NoArgsConstructor
@Getter
public class Disposal {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /**
   * 쓰레기 배출 가능 시간
   */
  private String disposableTime;

  /**
   * 쓰레기 배출 가능 요일
   */
  private String disposableDay;

  @ManyToOne
  @JoinColumn(name = "region_category_id")
  private RegionCategory regionCategory;

}