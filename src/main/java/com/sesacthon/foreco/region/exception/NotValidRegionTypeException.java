package com.sesacthon.foreco.region.exception;

import com.sesacthon.global.exception.BusinessException;
import com.sesacthon.global.exception.ErrorCode;

public class NotValidRegionTypeException extends BusinessException {

  public NotValidRegionTypeException(ErrorCode errorCode) {
    super(errorCode);
  }

}
