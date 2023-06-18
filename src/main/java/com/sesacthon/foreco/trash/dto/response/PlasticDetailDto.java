package com.sesacthon.foreco.trash.dto.response;

import static com.sesacthon.foreco.trash.entity.DisposalType.*;

import com.sesacthon.foreco.disposal.dto.response.DisposalInfoDto;
import com.sesacthon.foreco.disposal.entity.Disposal;
import com.sesacthon.foreco.trash.entity.DisposalType;
import com.sesacthon.foreco.trash.entity.Trash;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class PlasticDetailDto {

  private final DisposalType isRecyclable = RECYCLABLE;
  private final List<PlasticInfoDto> plasticInfo;
  private final DisposalInfoDto disposalInfo;

  //TODO: 예시 플라스틱 이미지 추가하기
  private final List<TrashSimpleDto> recommendTrashes = new ArrayList<>();

  public PlasticDetailDto(List<Trash> trashes, List<Disposal> disposals) {
    this.plasticInfo = trashes.stream()
        .map(PlasticInfoDto::new)
        .collect(Collectors.toList());
    this.disposalInfo = new DisposalInfoDto(disposals);

  }

}
