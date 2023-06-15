package com.sesacthon.foreco.category.dto.response;

import com.sesacthon.foreco.category.entity.Category;
import lombok.Getter;

@Getter
public class CategorySimpleDto {

  private final Long categoryId;
  private final String categoryName;
  private String iconUrl;

  public CategorySimpleDto(Category category) {
    this.categoryId = category.getId();
    this.categoryName = category.getTrashType();
  }

  public CategorySimpleDto(Long id, String name, String iconUrl) {
    this.categoryId = id;
    this.categoryName = name;
    this.iconUrl = iconUrl;
  }

}
