package com.sesacthon.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {


  /**
   * 공통적으로 사용되는 에러코드
   */
  INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "C001", "값이 올바르지 않습니다."),
  METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "C002", "지원하지 않는 Http Method 입니다."),
  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C003", "서버 에러"),
  INVALID_TYPE_VALUE(HttpStatus.BAD_REQUEST, "C004", "입력 값의 타입이 올바르지 않습니다."),
  HANDLE_ACCESS_DENIED(HttpStatus.FORBIDDEN, "C005", "접근이 거부 되었습니다."),
  RESOURCE_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "C006", "인증이 필요합니다. 로그인을 해주세요."),
  RESOURCE_FORBIDDEN(HttpStatus.FORBIDDEN, "C007", "해당 리소스에 대한 권한이 없습니다."),

  // Member
  MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "M001", "해당 uuid를 가진 멤버를 찾을 수 없습니다."),

  // JWT
  REFRESH_JWT_EXPIRED(HttpStatus.UNAUTHORIZED, "J003", "만료된 리프레시 토큰입니다."),

  /**
   * 도메인(entity)에 따른 에러코드 - 추가 예정
   */
  //카테고리
  CATEGORY_NOT_FOUND(HttpStatus.BAD_REQUEST, "C001", "해당 카테고리 배출 정보가 없습니다."),

  //세부 품목 쓰레기
  DISPOSAL_TYPE_ENUM_NOT_SUPPORTED(HttpStatus.BAD_REQUEST, "T001", "해당 배출형태의 정보가 없습니다."),
  TRASH_NOT_FOUND(HttpStatus.BAD_REQUEST, "T002", "현재 지역의 해당 쓰레기 배출 정보가 없습니다."),

  //관련 쓰레기
  RELATED_TRASH_NOT_FOUND(HttpStatus.BAD_REQUEST, "T003", "해당 쓰레기의 관련 쓰레기 정보가 없습니다."),

  //AI
  AI_SERVER_CONNECTION_FAILED(HttpStatus.SERVICE_UNAVAILABLE, "A001", "Ai 서버에 연결할 수 없습니다."),

  //이미지 저장
  IMAGE_WRONG_FILE_FORMAT(HttpStatus.BAD_REQUEST, "I001", "지원하지 않는 파일 확장자입니다."),

  //지역(Region)
  REGION_NOT_FOUND(HttpStatus.BAD_REQUEST, "R001", "등록되지 않은 지역입니다."),
  NOT_VALID_REGION_TYPE(HttpStatus.BAD_REQUEST, "R002", "입력 값의 타입이 올바르지 않습니다. 예시)서울시 동대문구 전능1동"),

  //동기부여(Motivation)
  MOTIVATION_NOT_FOUND(HttpStatus.BAD_REQUEST, "M001", "현재 쓰레기 관련 동기부여 정보가 없습니다."),

  //배출정보(Disposal)
  DISPOSAL_NOT_FOUND(HttpStatus.BAD_REQUEST,"D001", "현재 조회 가능한 배출 정보가 없습니다."),

  //Mission
  MISSION_NOT_FOUND(HttpStatus.NOT_FOUND,"MS001","조회 가능한 미션 정보가 없습니다."),
  EXCEEDS_MAXIMUM_PARTICIPATION(HttpStatus.FORBIDDEN,"MS002","해당 미션에 참여가능한 횟수를 초과했습니다.");


  private final HttpStatus status;
  private final String code;
  private final String message;

  public int getStatus() {
    return this.status.value();
  }

  ErrorCode(HttpStatus status, String code, String message) {
    this.status = status;
    this.code = code;
    this.message = message;
  }
}
