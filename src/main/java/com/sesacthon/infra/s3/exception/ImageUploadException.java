package com.sesacthon.infra.s3.exception;

import com.sesacthon.global.exception.ErrorCode;
import com.sesacthon.global.exception.BusinessException;

public class ImageUploadException extends BusinessException {
  public ImageUploadException(ErrorCode errorCode) {
    super(errorCode);
  }

}
