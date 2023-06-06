package com.sesacthon.infra.s3.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class S3Dto {

  /**
   * 이미지 url.
   */
  private String imgUrl;

}
