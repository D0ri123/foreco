package com.sesacthon.foreco.disposal.entity;

import static jakarta.persistence.FetchType.*;

import com.sesacthon.foreco.region.entity.Region;
import com.sesacthon.foreco.category.entity.Category;
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
  private String time;

  /**
   * 쓰레기 배출 가능 요일
   */
  private String day;

  /**
   * 배출 가능 카테고리
   */
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "category_id")
  private Category category;

  /**
   * 지역 정보
   */
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "region_id")
  private Region region;

}