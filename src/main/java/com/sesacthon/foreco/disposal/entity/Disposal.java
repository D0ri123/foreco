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
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 지역 기반 쓰레기 배출 정보.
 * - 요일, 시간, 배출 가능 품목
 */
@Entity
@NoArgsConstructor
@Getter
public class Disposal {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String time;

  private String day;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "category_id")
  private Category category;

  @OneToOne(fetch = LAZY)
  @JoinColumn(name = "region_id")
  private Region region;

}