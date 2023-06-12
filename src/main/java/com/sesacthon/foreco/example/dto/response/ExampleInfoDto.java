package com.sesacthon.foreco.example.dto.response;

import com.sesacthon.foreco.example.entity.Example;
import lombok.Getter;

/**
 * 관련 예시 쓰레기 품목 응답 Dto
 */
@Getter
public class ExampleInfoDto {

  private final String imgUrl;
  private final String trashName;
  private final String disposalMethod;

  public ExampleInfoDto(Example example) {
    this.imgUrl = example.getImgUrl();
    this.trashName = example.getTrashName();
    this.disposalMethod = example.getDisposalMethod();
  }

}
