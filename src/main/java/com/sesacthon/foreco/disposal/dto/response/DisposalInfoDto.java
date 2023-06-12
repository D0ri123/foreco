package com.sesacthon.foreco.disposal.dto.response;

import com.sesacthon.foreco.disposal.entity.Disposal;
import lombok.Getter;

/**
 * 배출 가능 요일, 시간 응답 Dto.
 */
@Getter
public class DisposalInfoDto {

  private final String day;
  private final String time;

  public DisposalInfoDto(Disposal disposal) {
    this.day = disposal.getDay();
    this.time = disposal.getTime();
  }


}
