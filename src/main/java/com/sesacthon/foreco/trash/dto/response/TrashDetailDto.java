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

  private final Long categoryId;

  private final String trashIcon;

  private final DisposalType isRecyclable;

  /**
   * 쓰레기 이름
   */
  private final String trashName;

  /**
   * 쓰레기 배출방법
   */
  private final List<String> disposalMethod;

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
          new TrashSimpleDto(9L, "천", "https://trash-s3-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%8E%E1%85%A5%E1%86%AB.png"),
          new TrashSimpleDto(10L, "현수막", "https://trash-s3-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%92%E1%85%A7%E1%86%AB%E1%84%89%E1%85%AE%E1%84%86%E1%85%A1%E1%86%A8.png"),
          new TrashSimpleDto(11L, "랩", "https://trash-s3-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%85%E1%85%A2%E1%86%B8.png"),
          new TrashSimpleDto(12L, "우비", "https://trash-s3-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%8B%E1%85%AE%E1%84%87%E1%85%B5.png"),
          new TrashSimpleDto(13L, "우산", "https://trash-s3-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%8B%E1%85%AE%E1%84%89%E1%85%A1%E1%86%AB.png")
      )
  );


  public TrashDetailDto(Trash trash, List<Disposal> disposals, List<Example> examples) {
    this.orderOfTab = trash.getOrderOfTab();
    this.maxNumOfTab = trash.getMaxNumOfTab();
    this.titleOfTab = trash.getTabTitle();
    this.id = trash.getId();
    this.categoryId = trash.getCategory().getId();
    this.trashIcon = trash.getTrashIcon();
    this.isRecyclable = trash.getType();
    this.trashName = trash.getTrashName();
    this.disposalMethod = parsingRemark(trash.getMethod());
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
