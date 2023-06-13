package com.sesacthon.foreco.expectedOutput.dto.response;

import com.sesacthon.foreco.expectedOutput.entity.ExpectedOutput;
import lombok.Getter;

@Getter
public class ExpectedOutputDto {

  private final String imgUrl;
  private final String imgName;


  public ExpectedOutputDto(ExpectedOutput output) {
    this.imgUrl = output.getOutputImg();
    this.imgName = output.getOutputName();
  }

}
