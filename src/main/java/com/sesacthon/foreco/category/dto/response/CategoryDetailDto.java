package com.sesacthon.foreco.category.dto.response;

import com.sesacthon.foreco.category.entity.Category;
import com.sesacthon.foreco.disposal.dto.response.DisposalInfoDto;
import com.sesacthon.foreco.disposal.entity.Disposal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.Getter;

@Getter
public class CategoryDetailDto {

  private final String categoryName;
  private final String disposalMethod;
  private final DisposalInfoDto disposalInfo;

  //TODO: 고정시킬 데이터 정하기
  private final List<CategorySimpleDto> recommendCategories = new ArrayList<>(
      Arrays.asList(
          new CategorySimpleDto(100L, "신문지"),
          new CategorySimpleDto(101L, "우유팩"),
          new CategorySimpleDto(102L, "전단지")
      )
  );


  public CategoryDetailDto(Category category, List<Disposal> disposalList) {
    this.categoryName = category.getTrashType();
    this.disposalMethod = category.getCategoryMethod();
    this.disposalInfo = new DisposalInfoDto(disposalList);
  }

}
