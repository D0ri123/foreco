package com.sesacthon.foreco.disposal.dto.response;

import com.sesacthon.foreco.disposal.entity.Disposal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.Getter;

/**
 * 배출 가능 요일, 시간 응답 Dto.
 */
@Getter
public class DisposalInfoDto {

  private final List<String> days;
  private final String time;

  public DisposalInfoDto(List<Disposal> disposals) {
    List<String> days = new ArrayList<>();
    for (Disposal disposal : disposals) {
      days.add(disposal.getDisposableDay());
    }
    this.days = days;
    this.time = disposals.get(0).getDisposableTime();
    //TODO
    /**
     * 저희 ui랑 api랑 기존까지 작업에서 모든 배출요일의 시간이 동일하다는 가정하에 작업이 되어있어서.
     * 우선 기존 방식에 영향이 가지 않도록 코드를 구성했습니다.
     * 이번에 수정했던 disposal테이블 처럼, 배출 요일별로 시간이 다른 경우를 사용자게에 보여주기 위해서 UI부터 ~ api응답까지 봐꿔야 할것 같아요.
     * 시간이 없으니 제출 이후에 다같이 이야기 해보는게 좋을것 같습니다.
     */
  }

}
