package com.sesacthon.foreco.trash.dto.response;

import com.sesacthon.foreco.example.dto.response.ExampleSimpleDto;
import com.sesacthon.foreco.trash.entity.Trash;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class PlasticInfoDto {
  private final Long categoryId;
  private final String name;
  private final List<String> disposalMethod;
  private final List<ExampleSimpleDto> examples;
  private final List<String> remark;
  private final String trashIcon;


  public PlasticInfoDto(Trash trash) {
    this.categoryId = trash.getCategory().getId();
    this.name = trash.getTrashName();
    this.disposalMethod = parsingRemark(trash.getMethod());
    this.examples = trash.getExamples().stream()
        .map(ExampleSimpleDto::new)
        .collect(Collectors.toList());
    this.remark = parsingRemark(trash.getRemark());
    this.trashIcon = trash.getTrashIcon();
  }

  private List<String> parsingRemark(String remark) {
    return Arrays.stream(remark.split("&")).toList();
  }

}
