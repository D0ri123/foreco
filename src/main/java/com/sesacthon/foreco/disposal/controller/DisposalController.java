package com.sesacthon.foreco.disposal.controller;

import com.sesacthon.foreco.disposal.dto.response.TodayDisposableCategoriesDto;
import com.sesacthon.foreco.disposal.service.DisposalService;
import com.sesacthon.global.response.DataResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name="쓰레기 배출 정보", description = "배출정보 관련 api")
@RestController
@RequiredArgsConstructor
public class DisposalController {

  private final DisposalService disposalService;

  /**
   * 메인 화면에서 사용자의 지역정보와 오늘 날짜를 기준으로 배출 가능 품목을 안내
   * @return 배출 가능 카테고리 응답 dto
   */
  @Operation(summary = "배출 정보", description = "요일에 해당하는 배출 가능 품목과 시간을 조회할 수 있습니다.")
  @GetMapping("/api/v1/disposal")
  public ResponseEntity<DataResponse<TodayDisposableCategoriesDto>> getDisposalInfoWithCond(@RequestParam("day") String day) {
    TodayDisposableCategoriesDto disposalInfo = disposalService.getDisposalInfo(day);
    return new ResponseEntity<>(
        DataResponse.of(HttpStatus.OK, "요일에 해당하는 배출정보 조회 성공", disposalInfo), HttpStatus.OK);
  }


}
