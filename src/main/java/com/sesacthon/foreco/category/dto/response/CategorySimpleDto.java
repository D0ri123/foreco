package com.sesacthon.foreco.category.dto.response;

import com.sesacthon.foreco.category.entity.Trash;
import lombok.Getter;

@Getter
public class CategorySimpleDto {

  private final Long categoryId;
  private final String categoryName;

  public CategorySimpleDto(Trash trash) {
    this.categoryId = trash.getId();
    this.categoryName = trash.getParentTrash().getTrashName();
  }

  public CategorySimpleDto(Long id, String name) {
    this.categoryId = id;
    this.categoryName = name;
  }

}
