package com.sesacthon.foreco.category.entity;

import static jakarta.persistence.EnumType.*;
import static jakarta.persistence.FetchType.*;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
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
  private String trashName;

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
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "parent_trash_id", referencedColumnName = "trashName")
  private Trash parentTrash;

  @OneToMany(mappedBy = "parentTrash")
  private List<Trash> childCategories;

}
