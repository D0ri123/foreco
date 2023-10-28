package com.sesacthon.foreco.mission.exception;

import com.sesacthon.global.exception.BusinessException;
import com.sesacthon.global.exception.ErrorCode;

public class MissionNotFountException extends BusinessException {

  public MissionNotFountException(ErrorCode errorCode) {
    super(errorCode);
  }
}
