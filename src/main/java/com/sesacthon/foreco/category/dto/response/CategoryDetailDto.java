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
          new CategorySimpleDto(18L, "종이 쇼핑백", "https://trash-s3-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%8C%E1%85%A9%E1%86%BC%E1%84%8B%E1%85%B5%E1%84%89%E1%85%AD%E1%84%91%E1%85%B5%E1%86%BC%E1%84%87%E1%85%A2%E1%86%A8.png"),
          new CategorySimpleDto(19L, "영수증", "https://trash-s3-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%8B%E1%85%A7%E1%86%BC%E1%84%89%E1%85%AE%E1%84%8C%E1%85%B3%E1%86%BC.png"),
          new CategorySimpleDto(20L, "사무용지", "https://trash-s3-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%89%E1%85%A1%E1%84%86%E1%85%AE%E1%84%8B%E1%85%AD%E1%86%BC%E1%84%8C%E1%85%B5.png"),
          new CategorySimpleDto(21L, "공책", "https://trash-s3-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%80%E1%85%A9%E1%86%BC%E1%84%8E%E1%85%A2%E1%86%A8.png"),
          new CategorySimpleDto(22L, "신문", "https://trash-s3-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%89%E1%85%B5%E1%86%AB%E1%84%86%E1%85%AE%E1%86%AB.png"),
          new CategorySimpleDto(23L, "폐휴지", "https://trash-s3-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%91%E1%85%A8%E1%84%92%E1%85%B2%E1%84%8C%E1%85%B5.png")
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
