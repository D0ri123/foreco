package com.sesacthon.foreco.disposal.dto.response;

import com.sesacthon.foreco.disposal.entity.Disposal;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

/**
 * 배출 가능 요일, 시간 응답 Dto.
 */
@Getter
public class DisposalInfoDto {

  // private final List<String> days 로 올려야 한다.
  private final List<String> days;
  private final String time;

//  public DisposalInfoDto(Disposal disposal) {
//    this.day = disposal.getDay();
//    this.time = disposal.getTime();
//  }

  public DisposalInfoDto(List<Disposal> disposals) {
    this.days = new ArrayList<>();
    for(Disposal disposal : disposals){
      days.add(disposal.getDay());
    }
    this.time = disposals.get(0).getTime();

  }


}
