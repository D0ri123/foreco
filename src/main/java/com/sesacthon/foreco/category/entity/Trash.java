package com.sesacthon.foreco.category.entity;

import static jakarta.persistence.EnumType.*;
import static jakarta.persistence.FetchType.LAZY;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;


/**
 * 쓰레기 품목(카테고리).
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
  private String name;

  /**
   * 쓰레기 보기 형식
   */
  @Enumerated(STRING)
  private ViewType viewType;

  /**
   * 쓰레기 아이콘 이름
   */
  private String trashIcon;

  /**
   * 상위 카테고리 Id.
   * 해당 데이터가 상위 카테고리라면 null
   */
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "trash_id")
  private Trash parentTrash;

  @OneToMany(mappedBy = "parentTrash")
  private List<Trash> childCategories;

  /**
   * 지역 카테고리(RegionCategory)
   */
  @OneToMany(mappedBy = "trash")
  List<RegionCategory> regionCategories= new ArrayList<>();
}
