package com.sesacthon.foreco.trash.dto;

import lombok.Getter;

@Getter
public class SearchedTrashDto {

  private final Long id;
  private final String name;
  private final String iconUrl;

  public SearchedTrashDto(Long id, String name, String iconUrl) {
    this.id = id;
    this.name = name;
    this.iconUrl = iconUrl;
  }
}
