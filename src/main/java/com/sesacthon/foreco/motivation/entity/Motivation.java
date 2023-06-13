package com.sesacthon.foreco.motivation.entity;

import static jakarta.persistence.FetchType.*;

import com.sesacthon.foreco.category.entity.Category;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Motivation {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /**
   * 재활용 과정
   */
  private String recyclingProc;

  /**
   * 재활용을 하지 않았을 때의 소개글
   */
  private String ifNot;

  @OneToOne(fetch = LAZY)
  @JoinColumn(name = "category_id")
  private Category category;


}
