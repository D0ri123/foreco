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

  /**
   * 도메인(entity)에 따른 에러코드 - 추가 예정
   */
  //카테고리
  CATEGORY_NOT_FOUND(HttpStatus.BAD_REQUEST, "C001", "해당 카테고리 배출 정보가 없습니다."),

  //세부 품목 쓰레기
  DISPOSAL_TYPE_ENUM_NOT_SUPPORTED(HttpStatus.BAD_REQUEST, "T001", "해당 배출형태의 정보가 없습니다."),
  TRASH_NOT_FOUND(HttpStatus.BAD_REQUEST, "T002", "현재 지역의 해당 쓰레기 배출 정보가 없습니다."),

  //이미지 저장
  IMAGE_WRONG_FILE_FORMAT(HttpStatus.BAD_REQUEST, "I001", "지원하지 않는 파일 확장자입니다."),

  //지역(Region)
  REGION_NOT_FOUND(HttpStatus.BAD_REQUEST, "R001", "등록되지 않은 지역입니다."),
  NOT_VALID_REGION_TYPE(HttpStatus.BAD_REQUEST, "R002", "입력 값의 타입이 올바르지 않습니다. 예시)서울시 동대문구 전능1동");


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
