package com.sesacthon.global.response;

import lombok.Getter;

/**
 * 공통된 응답 형식을 제공하기 위한 응답 Dto.
 */
@Getter
public abstract class DefaultResponse {

  /**
   * 응답 코드
   */
  protected int status;


  /**
   * 응답 메시지
   */
  protected String message;

  protected DefaultResponse(int status, String message) {
    this.status = status;
    this.message = message;
  }
}
