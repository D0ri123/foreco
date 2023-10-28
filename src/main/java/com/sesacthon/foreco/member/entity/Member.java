package com.sesacthon.foreco.member.entity;

import com.github.f4b6a3.uuid.UuidCreator;
import com.sesacthon.foreco.common.BaseTimeEntity;
import com.sesacthon.foreco.disposal.controller.region.entity.Region;
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
   * 역할(Guest, Social)
   */
  @Enumerated(EnumType.STRING)
  private Role role;

  /**
   * 멤버가 가지고 있는 리워드 총 포인트
   */
  private Long totalPoint;

  /**
   * 회원번호 (oauthProvider#회원번호)
   * 게스트 로그인한 사용자의 이름을 나타내기 위해서 생성했습니다.
   */
  private String userNumber;

  /**
   * SNS 로그인한 사용자의 이름
   * 게스트 로그인 사용자의 이름은 NONE으로 설정했습니다.
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
   * 지역
   */
  @ManyToOne
  @JoinColumn(name="region_id")
  private Region region;

  @PrePersist
  public void createId() {
    this.id = UuidCreator.getTimeOrdered();
  }

  @Builder
  public Member(String profileUrl, String userNumber,
      Role role, OAuth2Provider oauth2Provider, String username) {
    this.userNumber = userNumber;
    this.username = username;
    this.profileUrl = profileUrl;
    this.role = role;
    this.oauth2Provider = oauth2Provider;
  }

  public void updateInfo(String profileUrl, String username, Region region) {
    this.profileUrl = profileUrl;
    this.username = username;
    this.region = region;
  }

  public void setRegion(Region region) {
    this.region = region;
  }

}
