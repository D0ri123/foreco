package com.sesacthon.foreco.disposal.dto.response;

import lombok.Getter;

/**
 * 배출 가능 품목 id, 이름 응답 dto
 */
@Getter
public class DisposableCategoryDto {

  /**
   * 카테고리 고유 Id.
   */
  private final Long categoryId;

  /**
   * 카테고리 이름
   */
  private final String categoryName;
  private final String iconUrl;

  public DisposableCategoryDto(Long categoryId, String categoryName, String iconUrl) {
    this.categoryId = categoryId;
    this.categoryName = categoryName;
    this.iconUrl = iconUrl;
  }

}
