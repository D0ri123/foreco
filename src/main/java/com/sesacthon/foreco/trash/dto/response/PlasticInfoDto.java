package com.sesacthon.foreco.trash.dto.response;

import com.sesacthon.foreco.example.dto.response.ExampleSimpleDto;
import com.sesacthon.foreco.trash.entity.Trash;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class PlasticInfoDto {
  private final String name;
  private final String disposalMethod;
  private final List<ExampleSimpleDto> emamples;
  private final List<String> remark;

  public PlasticInfoDto(Trash trash) {
    this.name = trash.getTrashName();
    this.disposalMethod = trash.getMethod();
    this.emamples = trash.getExamples().stream()
        .map(ExampleSimpleDto::new)
        .collect(Collectors.toList());
    this.remark = parsingRemark(trash.getRemark());
  }

  private List<String> parsingRemark(String remark) {
    return Arrays.stream(remark.split("&")).toList();
  }

}
