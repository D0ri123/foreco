package com.sesacthon.foreco.trash.dto.response;

import static com.sesacthon.foreco.trash.entity.DisposalType.*;

import com.sesacthon.foreco.disposal.dto.response.DisposalInfoDto;
import com.sesacthon.foreco.disposal.entity.Disposal;
import com.sesacthon.foreco.trash.entity.DisposalType;
import com.sesacthon.foreco.trash.entity.Trash;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class PlasticDetailDto {

  private final DisposalType isRecyclable = RECYCLABLE;
  private final List<PlasticInfoDto> plasticInfo;
  private final DisposalInfoDto disposalInfo;

  private final List<TrashSimpleDto> recommendTrashes = new ArrayList<>(
      Arrays.asList(
          new TrashSimpleDto(18L, "세제 용기"),
          new TrashSimpleDto(19L, "배달 용기"),
          new TrashSimpleDto(26L, "요구르트병"),
          new TrashSimpleDto(27L, "다리미"),
          new TrashSimpleDto(35L, "칫솔"),
          new TrashSimpleDto(36L, "양념통")
      )
  );
  public PlasticDetailDto(List<Trash> trashes, List<Disposal> disposals) {
    this.plasticInfo = trashes.stream()
        .map(PlasticInfoDto::new)
        .collect(Collectors.toList());

    this.disposalInfo = new DisposalInfoDto(disposals);
  }

}
