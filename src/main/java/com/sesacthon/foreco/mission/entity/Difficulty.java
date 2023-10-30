package com.sesacthon.foreco.mission.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Difficulty {
  LOW("하")
  , MIDDLE("중")
  , HIGH("상");

  private final String name;

}
