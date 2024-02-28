package com.sesacthon.foreco.member.service;

import com.sesacthon.foreco.jwt.dto.ReIssueTokenDto;
import com.sesacthon.foreco.jwt.service.JwtTokenProvider;
import com.sesacthon.foreco.member.dto.response.LoginResponseDto;
import com.sesacthon.foreco.member.dto.response.MemberSimpleInfoResponse;
import com.sesacthon.foreco.member.entity.HousingType;
import com.sesacthon.foreco.member.entity.Member;
import com.sesacthon.foreco.member.entity.OAuth2Provider;
import com.sesacthon.foreco.region.entity.Region;
import com.sesacthon.foreco.region.service.RegionService;
import com.sesacthon.global.util.CookieUtil;
import com.sesacthon.global.util.HttpHeaderUtil;
import com.sesacthon.infra.feign.dto.response.KakaoUserInfoResponseDto;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberSignUpService {

  private final JwtTokenProvider jwtTokenProvider;
  private final MemberService memberService;
  private final RegionService regionService;

  public LoginResponseDto loginKakaoMember(KakaoUserInfoResponseDto kakaoUserInfo, String region, String housingType) {
    String userNumber = String.format("%s#%s", OAuth2Provider.KAKAO, kakaoUserInfo.getId());
    Optional<Member> loginMember = memberService.getMemberByUserNumber(userNumber);
    Region memberRegion = regionService.findRegion(region);
    HousingType userType = HousingType.valueOf(housingType);

    if(loginMember.isPresent()) {
      Member member = loginMember.get();
      updateMemberInfo(kakaoUserInfo, member);
      return createSignUpResult(member);
    }
    Member member = signUp(kakaoUserInfo, memberRegion, userType);
    return createSignUpResult(member);
  }

  /**
   * member 정보를 가지고 accessToken 과 refreshToken 을 생성한다.
   */
  private LoginResponseDto createSignUpResult(Member member) {
    String accessToken = jwtTokenProvider.createAccessToken(member.getId(), member.getOauth2Provider());
    String refreshToken = jwtTokenProvider.createRefreshToken(member.getId());

    // refreshToken은 redis에 따로 저장해둔다.
    jwtTokenProvider.saveRefreshTokenInRedis(member, refreshToken);
    return new LoginResponseDto(accessToken, refreshToken, new MemberSimpleInfoResponse(member));
  }

  private Member signUp(KakaoUserInfoResponseDto kakaoUserInfo, Region region, HousingType housingType) {
    Member member = kakaoUserInfo.toEntity(region, housingType);
    //repository에 저장하기 이전에 region을 member의 region으로 넣고싶다.
    return memberService.saveInfo(member);
  }

  private void updateMemberInfo(KakaoUserInfoResponseDto kakaoUserInfo, Member member) {
    member.updateInfo(kakaoUserInfo.getProfileImg(), kakaoUserInfo.getUsername());
  }

  public static HttpHeaders setCookieAndHeader(LoginResponseDto loginResult) {
    HttpHeaders headers = new HttpHeaders();
    CookieUtil.setRefreshCookie(headers, loginResult.getRefreshToken());
    HttpHeaderUtil.setAccessToken(headers, loginResult.getAccessToken());
    return headers;
  }

  public static HttpHeaders setCookieAndHeader(ReIssueTokenDto reIssueTokenDto) {
    HttpHeaders headers = new HttpHeaders();
    HttpHeaderUtil.setAccessToken(headers, reIssueTokenDto.getAccessToken());
    CookieUtil.setRefreshCookie(headers, reIssueTokenDto.getRefreshToken());
    return headers;
  }

}
