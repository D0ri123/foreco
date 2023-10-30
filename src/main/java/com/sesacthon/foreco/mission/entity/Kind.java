package com.sesacthon.foreco.mission.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Kind {
  WIZ("Wiz"),
  ETC("Etc");

  private final String name;

}
