package com.sesacthon.foreco.trash.dto.response;

import com.sesacthon.foreco.disposal.dto.response.DisposalInfoDto;
import com.sesacthon.foreco.disposal.entity.Disposal;
import com.sesacthon.foreco.example.dto.response.ExampleInfoDto;
import com.sesacthon.foreco.example.entity.Example;
import com.sesacthon.foreco.trash.entity.DisposalType;
import com.sesacthon.foreco.trash.entity.Trash;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

/**
 * 상세품목 쓰레기 응답 Dto
 */
@Getter
public class TrashDetailDto {
  private final int orderOfTab;
  private final int maxNumOfTab;
  private final String titleOfTab;

  /**
   * 쓰레기 고유 Id
   */
  private final Long id;

  private final DisposalType isRecyclable;

  /**
   * 쓰레기 이름
   */
  private final String trashName;

  /**
   * 쓰레기 배출방법
   */
  private final String disposalMethod;

  /**
   * 쓰레기 배출 가능 시간 정보
   */
  private final DisposalInfoDto disposalInfo;

  /**
   * 쓰레기 유의사항
   */
  private final List<String> remark;

  /**
   * 관련 쓰레기 예시
   */
  private final List<ExampleInfoDto> examples;

  /**
   * 재질이 동일한 쓰레기 정보
   */
  private final List<TrashSimpleDto> recommendTrashes = new ArrayList<>(
      Arrays.asList(
          new TrashSimpleDto(41L, "천"),
          new TrashSimpleDto(42L, "현수막"),
          new TrashSimpleDto(43L, "에어캡(뽁뽁이)"),
          new TrashSimpleDto(44L, "랩"),
          new TrashSimpleDto(45L, "우비"),
          new TrashSimpleDto(46L, "우산")
      )
  );


  public TrashDetailDto(Trash trash, List<Disposal> disposals, List<Example> examples) {
    this.orderOfTab = trash.getOrderOfTab();
    this.maxNumOfTab = trash.getMaxNumOfTab();
    this.titleOfTab = trash.getTabTitle();
    this.id = trash.getId();
    this.isRecyclable = trash.getType();
    this.trashName = trash.getTrashName();
    this.disposalMethod = trash.getMethod();
    this.remark = parsingRemark(trash.getRemark());

    //조건을 만족하는 Disposal 데이터를 가져왔다.
    this.disposalInfo = new DisposalInfoDto(disposals);
    this.examples = examples.stream()
        .map(ExampleInfoDto::new)
        .collect(Collectors.toList());
  }

  private List<String> parsingRemark(String remark) {
    return Arrays.stream(remark.split("&")).toList();
  }


}
