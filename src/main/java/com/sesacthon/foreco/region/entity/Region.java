package com.sesacthon.foreco.region.entity;

import static jakarta.persistence.FetchType.*;

import com.sesacthon.foreco.trash.entity.Trash;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 지역 관련 정보.
 */
@Entity
@NoArgsConstructor
@Getter
public class Region {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String city;

  private String gu;

  private String dong;

  /**
   * 지역 기반 쓰레기 처리 정보.
   */
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "trash_id")
  private Trash trash;

}
