package com.sesacthon.foreco.motivation.dto.response;

import lombok.Getter;

@Getter
public class ProcessDto {

  private final String title;
  private final String content;

  public ProcessDto(String title, String content) {
    this.title = title;
    this.content = content;
  }

}
