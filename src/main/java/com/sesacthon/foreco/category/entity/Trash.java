package com.sesacthon.foreco.category.entity;

import static jakarta.persistence.EnumType.*;
import static jakarta.persistence.FetchType.LAZY;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sesacthon.foreco.common.Icon;
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
import org.hibernate.annotations.BatchSize;


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
  @Enumerated(STRING)
  private Icon trashIcon;

  /**
   * 상위 카테고리 Id.
   * 해당 데이터가 상위 카테고리라면 null
   */
  @JsonBackReference
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "trash_id")
  private Trash parentTrash;

  @JsonManagedReference
  @BatchSize(size = 100)
  @OneToMany(mappedBy = "parentTrash")
  private List<Trash> childTrashes = new ArrayList<>();

  /**
   * 지역 카테고리(RegionCategory)
   */
  @OneToMany(mappedBy = "trash")
  List<RegionCategory> regionCategories= new ArrayList<>();

}
