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
  HANDLE_ACCESS_DENIED(HttpStatus.FORBIDDEN, "C005", "접근이 거부 되었습니다.");

  /**
   * 도메인(entity)에 따른 에러코드 - 추가 예정
   */


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
