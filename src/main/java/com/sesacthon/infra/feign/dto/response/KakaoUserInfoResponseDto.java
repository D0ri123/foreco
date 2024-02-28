package com.sesacthon.infra.feign.dto.response;

import com.sesacthon.foreco.member.entity.HousingType;
import com.sesacthon.foreco.member.entity.Member;
import com.sesacthon.foreco.member.entity.OAuth2Provider;
import com.sesacthon.foreco.region.entity.Region;
import lombok.Getter;

/**
 * 카카오 서버에서 어떤 정보를 가져올지
 */
@Getter
public class KakaoUserInfoResponseDto {

  /**
   * 회원 번호
   */
  private Long id;

  /**
   * 카카오 계정 정보
   */
  private KakaoAccount kakao_account;

  @Getter
  static class KakaoAccount {
    private Profile profile;
  }

  @Getter
  static class Profile {
    private String nickname;
    private String profile_image_url;
  }

  @Override
  public String toString() {
    return
        "id = " + this.id +
        "profile.nickname = " + this.kakao_account.profile.nickname +
            "profile.profile_img_url = " + this.kakao_account.profile.profile_image_url;
  }

  public String getProfileImg() {
    return this.getKakao_account().getProfile().profile_image_url;
  }

  public String getUsername() {
    return this.getKakao_account().getProfile().getNickname();
  }

  public String createUserNumber() {
    return String.format("%s#%s", OAuth2Provider.KAKAO, this.getId());
  }

  public Member toEntity(Region region, HousingType housingType) {
    return Member.builder()
        .username(this.getUsername())
        .userNumber(createUserNumber())
        .oauth2Provider(OAuth2Provider.KAKAO)
        .profileUrl(this.getProfileImg())
        .region(region)
        .housingType(housingType)
        .totalPoint(0L)
        .build();
  }
}
