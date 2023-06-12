package com.sesacthon.foreco.trash.dto.response;

import com.sesacthon.foreco.disposal.dto.response.DisposalInfoDto;
import com.sesacthon.foreco.disposal.entity.Disposal;
import com.sesacthon.foreco.trash.entity.Trash;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class PlasticDetailDto {

  private final List<PlasticInfoDto> plasticInfo;
  private final DisposalInfoDto disposalInfo;

  public PlasticDetailDto(List<Trash> trashes, List<Disposal> disposals) {
    this.plasticInfo = trashes.stream()
        .map(PlasticInfoDto::new)
        .collect(Collectors.toList());

    this.disposalInfo = new DisposalInfoDto(disposals);
  }

}
