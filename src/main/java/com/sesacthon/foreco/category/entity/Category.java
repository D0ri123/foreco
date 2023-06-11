package com.sesacthon.foreco.category.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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


}
