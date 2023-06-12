package com.sesacthon.foreco.example.dto.response;

import com.sesacthon.foreco.example.entity.Example;
import lombok.Getter;

@Getter
public class ExampleSimpleDto {

  private final String imgUrl;
  private final String trashName;

  public ExampleSimpleDto(Example example) {
    this.imgUrl = example.getImgUrl();
    this.trashName = example.getTrashName();
  }
}
