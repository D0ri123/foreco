package com.sesacthon.foreco.region.entity;

import com.sesacthon.foreco.category.entity.RegionCategory;
import com.sesacthon.foreco.member.entity.Member;
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
   * 회원(member)
   */
  @OneToMany(mappedBy = "region")
  private List<Member> members = new ArrayList<>();

  @OneToMany(mappedBy = "region")
  private List<RegionCategory> categories = new ArrayList<>();
}
