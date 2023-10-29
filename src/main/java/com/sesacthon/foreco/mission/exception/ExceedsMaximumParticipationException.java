package com.sesacthon.foreco.mission.exception;

import com.sesacthon.global.exception.BusinessException;
import com.sesacthon.global.exception.ErrorCode;

public class ExceedsMaximumParticipationException extends BusinessException {

  public ExceedsMaximumParticipationException(ErrorCode errorCode) {
    super(errorCode);
  }
}
