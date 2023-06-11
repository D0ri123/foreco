package com.sesacthon.foreco.category.dto.response;

import com.sesacthon.foreco.category.entity.Category;
import lombok.Getter;

@Getter
public class CategorySimpleDto {

  private final Long categoryId;
  private final String categoryName;

  public CategorySimpleDto(Category category) {
    this.categoryId = category.getId();
    this.categoryName = category.getTrashType();
  }

  public CategorySimpleDto(Long id, String name) {
    this.categoryId = id;
    this.categoryName = name;
  }

}
