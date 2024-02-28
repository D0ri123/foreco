package com.sesacthon.foreco.member.entity;

import com.github.f4b6a3.uuid.UuidCreator;
import com.sesacthon.foreco.common.BaseTimeEntity;
import com.sesacthon.foreco.region.entity.Region;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.UUID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Member extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(length = 16)
  private UUID id;

  /**
   * 멤버가 가지고 있는 리워드 총 포인트
   */
  private Long totalPoint;

  /**
   * 회원번호 (oauthProvider#회원번호)
   */
  private String userNumber;

  /**
   * SNS 로그인한 사용자의 이름
   */
  private String username;

  /**
   * profile 이미지 url
   */
  private String profileUrl;

  /**
   * OAuth 제공자
   */
  @Enumerated(EnumType.STRING)
  private OAuth2Provider oauth2Provider;

  /**
   * 주거 형태
   */
  @Enumerated(EnumType.STRING)
  private HousingType housingType;

  /**
   * 지역
   */
  @ManyToOne
  @JoinColumn(name = "region_id")
  private Region region;

  @PrePersist
  public void createId() {
    this.id = UuidCreator.getTimeOrdered();
  }

  @Builder
  public Member(String profileUrl, String userNumber, OAuth2Provider oauth2Provider,
      String username, Region region, HousingType housingType, Long totalPoint) {
    this.userNumber = userNumber;
    this.username = username;
    this.profileUrl = profileUrl;
    this.oauth2Provider = oauth2Provider;
    this.region = region;
    this.totalPoint = totalPoint;
    this.housingType = housingType;
  }

  public void updateInfo(String profileUrl, String username) {
    this.profileUrl = profileUrl;
    this.username = username;
  }

  public void updateTotalPoint(Long totalPoint){
    this.totalPoint = totalPoint;
  }

}
