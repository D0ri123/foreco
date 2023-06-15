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
  private final String categoryIcon;
  private final List<String> disposalMethod;
  private final List<String> remark;
  private final DisposalInfoDto disposalInfo;

  private final List<CategorySimpleDto> recommendCategories = new ArrayList<>(
      Arrays.asList(
          new CategorySimpleDto(8L, "종이 쇼핑백"),
          new CategorySimpleDto(13L, "영수증"),
          new CategorySimpleDto(10L, "사무용지"),
          new CategorySimpleDto(7L, "공책"),
          new CategorySimpleDto(6L, "신문"),
          new CategorySimpleDto(15L, "폐휴지")
      )
  );


  public CategoryDetailDto(Category category, List<Disposal> disposalList) {
    this.categoryName = category.getTrashType();
    this.categoryIcon = category.getCategoryIcon();
    this.disposalMethod = parsingRemark(category.getCategoryMethod());
    this.remark = parsingRemark(category.getRemark());
    this.disposalInfo = new DisposalInfoDto(disposalList);
  }

  private List<String> parsingRemark(String remark) {
    return Arrays.stream(remark.split("&")).toList();
  }

}
