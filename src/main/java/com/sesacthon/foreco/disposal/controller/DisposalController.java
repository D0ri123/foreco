package com.sesacthon.foreco.disposal.controller;

import com.sesacthon.foreco.disposal.dto.response.DisposableCategoryDto;
import com.sesacthon.foreco.disposal.service.DisposalService;
import com.sesacthon.foreco.region.service.RegionService;
import com.sesacthon.global.response.DataResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DisposalController {

  private final RegionService regionService;
  private final DisposalService disposalService;

  /**
   * 메인 화면에서 사용자의 지역정보와 오늘 날짜를 기준으로 배출 가능 품목을 안내
   * @param region 사용자의 지역정보(시,구,동)
   * @return 배출 가능 카테고리 응답 dto
   */
  @GetMapping("/api/v1/disposal")
  public ResponseEntity<DataResponse<List<DisposableCategoryDto>>> getDisposalInfoWithCond(
      @RequestParam("region") String region) {
    Long regionId = regionService.findRegion(region);
    List<DisposableCategoryDto> categories = disposalService.findCategoriesWithRegionAndDate(regionId);
    return new ResponseEntity<>(DataResponse.of(HttpStatus.OK, "해당 지역의 오늘 배출 정보 조회 성공", categories), HttpStatus.OK);
  }


}
