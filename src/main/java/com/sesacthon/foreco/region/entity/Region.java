package com.sesacthon.foreco.region.entity;

import com.sesacthon.foreco.trash.entity.Trash;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 지역 관련 정보.
 */
@Entity
@NoArgsConstructor
@Getter
@Table(indexes = {
    @Index(name = "idx__city__gu__dong", columnList = "city, gu, dong")
})
public class Region {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /**
   * 시
   */
  private String city;

  /**
   * 구
   */
  private String gu;

  /**
   * 동
   */
  private String dong;

  /**
   * 지역 기반 쓰레기 배출 정보 리스트
   */
  @OneToMany(mappedBy = "region")
  List<Trash> trashes = new ArrayList<>();

}
