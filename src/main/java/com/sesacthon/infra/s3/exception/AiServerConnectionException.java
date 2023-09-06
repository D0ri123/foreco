package com.sesacthon.infra.s3.exception;

import com.sesacthon.global.exception.BusinessException;
import com.sesacthon.global.exception.ErrorCode;

public class AiServerConnectionException extends BusinessException {

  public AiServerConnectionException(ErrorCode errorCode) {
    super(errorCode);
  }

}
