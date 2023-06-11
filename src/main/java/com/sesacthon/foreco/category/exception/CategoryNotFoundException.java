package com.sesacthon.foreco.category.exception;

import com.sesacthon.global.exception.BusinessException;
import com.sesacthon.global.exception.ErrorCode;

public class CategoryNotFoundException extends BusinessException {
  public CategoryNotFoundException(ErrorCode errorCode) {
    super(errorCode);
  }
}
