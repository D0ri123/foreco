package com.sesacthon.foreco.disposal.controller.region.exception;

import com.sesacthon.global.exception.BusinessException;
import com.sesacthon.global.exception.ErrorCode;

public class RegionNotFoundException extends BusinessException {
  public RegionNotFoundException(ErrorCode errorCode) {
    super(errorCode);
  }

}
