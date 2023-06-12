package com.sesacthon.foreco.category.entity;

import static jakarta.persistence.FetchType.LAZY;

import com.sesacthon.foreco.region.entity.Region;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 쓰레기 품목(카테고리).
 */
@Entity
@NoArgsConstructor
@Getter
public class Category {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /**
   * 카테고리 이름
   */
  private String trashType;

  /**
   * 카테고리의 일반적인 처리방법
   */
  private String categoryMethod;

  /**
   * 카테고리의 처리방법 유의사항
   */
  private String remark;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "region_id")
  private Region region;

}
