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
          new TrashSimpleDto(3L, "세제 용기", "https://trash-s3-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%89%E1%85%A6%E1%84%8C%E1%85%A6%E1%84%8B%E1%85%AD%E1%86%BC%E1%84%80%E1%85%B5.png"),
          new TrashSimpleDto(4L, "배달 용기", "https://trash-s3-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%87%E1%85%A2%E1%84%83%E1%85%A1%E1%86%AF%E1%84%8B%E1%85%AD%E1%86%BC%E1%84%80%E1%85%B5.png"),
          new TrashSimpleDto(5L, "요구르트병","https://trash-s3-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%8B%E1%85%AD%E1%84%80%E1%85%AE%E1%84%85%E1%85%B3%E1%84%90%E1%85%B3%E1%84%87%E1%85%A7%E1%86%BC.png"),
          new TrashSimpleDto(6L, "다리미", "https://trash-s3-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%83%E1%85%A1%E1%84%85%E1%85%B5%E1%84%86%E1%85%B5.png"),
          new TrashSimpleDto(7L, "칫솔", "https://trash-s3-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%8E%E1%85%B5%E1%86%BA%E1%84%89%E1%85%A9%E1%86%AF.png"),
          new TrashSimpleDto(8L, "양념통", "https://trash-s3-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%8B%E1%85%A3%E1%86%BC%E1%84%82%E1%85%A7%E1%86%B7%E1%84%90%E1%85%A9%E1%86%BC.png")
      )
  );
  public PlasticDetailDto(List<Trash> trashes, List<Disposal> disposals) {
    this.plasticInfo = trashes.stream()
        .map(PlasticInfoDto::new)
        .collect(Collectors.toList());
    this.disposalInfo = new DisposalInfoDto(disposals);

  }

}
