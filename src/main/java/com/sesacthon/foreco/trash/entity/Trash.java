package com.sesacthon.foreco.trash.entity;

import static jakarta.persistence.FetchType.*;

import com.sesacthon.foreco.category.entity.Category;

import com.sesacthon.foreco.example.entity.Example;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 쓰레기
 */
@Entity
@NoArgsConstructor
@Getter
public class Trash {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /**
   * 쓰레기 이름
   */
  private String trashName;

  /**
   * 쓰레기 상세 처리방법
   */
  private String method;

  /**
   * 쓰레기 재질
   */
  private String material;

  /**
   * 쓰레기 배출 특이사항
   */
  private String remark;

  /**
   * 쓰레기 처리형태
   */
  @Convert(converter = DisposalTypeConverter.class)
  private DisposalType type;

  @OneToMany(mappedBy = "trash")
  private List<Example> examples;

  /**
   * 쓰레기 카테고리
   */
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "category_id")
  private Category category;

}