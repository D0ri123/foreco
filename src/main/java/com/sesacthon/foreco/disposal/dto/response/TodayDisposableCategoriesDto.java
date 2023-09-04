package com.sesacthon.foreco.disposal.dto.response;

import java.util.List;
import lombok.Getter;

@Getter
public class TodayDisposableCategoriesDto {

  private final List<String> categoryNames;

  public TodayDisposableCategoriesDto(List<String> categoryNames) {
    this.categoryNames = categoryNames;
  }
}
