package com.sesacthon.foreco.member.controller;

import com.sesacthon.foreco.jwt.dto.SessionUser;
import com.sesacthon.foreco.member.dto.response.LoginResponseDto;
import com.sesacthon.foreco.member.dto.response.MemberInfoResponseDto;
import com.sesacthon.foreco.member.service.MemberInfoService;
import com.sesacthon.foreco.member.service.MemberSignUpService;
import com.sesacthon.global.response.DataResponse;
import com.sesacthon.infra.feign.dto.response.KakaoUserInfoResponseDto;
import com.sesacthon.infra.feign.service.KakaoFeignService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "멤버 관련 컨트롤러")
public class MemberController {

  private final KakaoFeignService kakaoFeignService;
  private final MemberSignUpService memberSignUpService;
  private final MemberInfoService memberInfoService;


  /**
   * 로그인 요청을 통해 인가코드를 redirect url로 발급 가능
   */
  @Operation(
      summary = "인가 코드 발급",
      description = "해당 url을 통해 로그인 화면으로 넘어간 후, 사용자가 정보를 입력하면 redirect url에서 코드를 발급할 수 있습니다.")
  @GetMapping("/api/v1/kakao/login")
  public ResponseEntity<HttpHeaders> getKakaoAuthCode(@RequestParam("redirectUri") String redirectUri)  {
    HttpHeaders httpHeaders = kakaoFeignService.kakaoLogin(redirectUri);
    return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
  }

  /**
   * 인가코드를 통해 accessToken 과 유저 정보를 가져온다.
   */
  @Operation(
      summary = "카카오 계정 회원가입",
      description = "인가 코드를 입력하고 요청보내면, 사용자의 정보를 저장한 후 사용자의 Id를 확인할 수 있습니다.")
  @GetMapping("/api/v1/account/kakao/result")
  public ResponseEntity<DataResponse<LoginResponseDto>> kakaoLogin(
      @RequestParam("code") String code, @RequestParam("redirectUri") String redirectUri) {

    //TODO: 쿠키, 헤더 사용하지 않고 바로 accessToken과 refreshToken을 발급해주는 방식으로 바꿀 예정
    //코드를 통해 액세스 토큰 발급한 후, 유저 정보를 가져온다.
    KakaoUserInfoResponseDto kakaoUserInfo = kakaoFeignService.getKakaoInfoWithToken(code, redirectUri);
    LoginResponseDto kakaoLoginResponse = memberSignUpService.loginKakaoMember(kakaoUserInfo);
    return new ResponseEntity<>(
        DataResponse.of(
            HttpStatus.CREATED, "카카오 계정으로 회원가입 성공", kakaoLoginResponse), HttpStatus.CREATED);
  }

  /**
   * 유저가 자기 자신의 정보에 대해 알 수 있다.
   */
  @Operation(
      summary = "내 정보 조회",
      description = "마이 페이지에서 사용자의 정보를 볼 수 있습니다.")
  @PreAuthorize("isAuthenticated()")
  @GetMapping("/api/v1/member")
  public ResponseEntity<DataResponse<MemberInfoResponseDto>> getMember(
      @AuthenticationPrincipal SessionUser sessionUser
  ) {
    MemberInfoResponseDto memberInfo = memberInfoService.getMember(sessionUser.getUuid());
    return new ResponseEntity<>(
        DataResponse.of(HttpStatus.OK, "멤버 정보 조회 성공", memberInfo), HttpStatus.OK);
  }


}
