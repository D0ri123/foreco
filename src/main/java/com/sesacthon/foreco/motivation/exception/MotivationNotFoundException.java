package com.sesacthon.foreco.motivation.exception;

import com.sesacthon.global.exception.BusinessException;
import com.sesacthon.global.exception.ErrorCode;

public class MotivationNotFoundException extends BusinessException {

  public MotivationNotFoundException(ErrorCode errorCode) {
    super(errorCode);
  }

}