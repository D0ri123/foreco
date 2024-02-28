package com.sesacthon.infra.feign.service;

import com.sesacthon.infra.feign.client.kakao.KakaoInfoClient;
import com.sesacthon.infra.feign.client.kakao.KakaoTokenClient;
import com.sesacthon.infra.feign.dto.KakaoInfo;
import com.sesacthon.infra.feign.dto.response.KakaoUserInfoResponseDto;
import com.sesacthon.infra.feign.dto.response.KakaoUserUnlinkResponseDto;
import java.net.URI;
import java.net.URISyntaxException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoFeignService {

  private final KakaoInfoClient kakaoInfoClient;
  private final KakaoInfo kakaoInfo;

  public HttpHeaders kakaoLogin(String redirectUri){
    return createHttpHeader(kakaoInfo.kakaoUrlInit(redirectUri));
  }

  private static HttpHeaders createHttpHeader(String requestUrl) {
    try {
      URI uri = new URI(requestUrl);
      HttpHeaders httpHeaders = new HttpHeaders();
      httpHeaders.setLocation(uri);
      return httpHeaders;
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * 카카오 액세스 토큰으로 유저 정보를 요청합니다.
   */
  public KakaoUserInfoResponseDto getKakaoInfo(String kakaoToken) {
    String token = "Bearer " + kakaoToken;
    return kakaoInfoClient.getUserInfo(token);
  }

  public KakaoUserUnlinkResponseDto unlinkService(String kakaoToken) {
    return kakaoInfoClient.unlinkUser(kakaoToken);
  }

}
