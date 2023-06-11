package com.sesacthon.foreco.trash.exception;

import com.sesacthon.global.exception.BusinessException;
import com.sesacthon.global.exception.ErrorCode;

public class TrashNotFoundException extends BusinessException {

  public TrashNotFoundException(ErrorCode errorCode) {
    super(errorCode);
  }

}
