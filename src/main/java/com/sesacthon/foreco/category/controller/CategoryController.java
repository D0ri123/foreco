package com.sesacthon.foreco.category.controller;

import com.sesacthon.foreco.category.dto.response.CategoryDetailDto;
import com.sesacthon.foreco.category.service.CategoryService;
import com.sesacthon.foreco.region.service.RegionService;
import com.sesacthon.global.response.DataResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name="쓰레기 카테고리", description = "카테고리 관련 api")
@RestController
@RequiredArgsConstructor
public class CategoryController {

  private final CategoryService categoryService;
  private final RegionService regionService;

  @Operation(summary = "상세 조회", description = "위치 정보와 카테고리 이름 정보로 배출방법을 조회할 수 있습니다.")
  @GetMapping("/api/v1/category")
  public ResponseEntity<DataResponse<CategoryDetailDto>> getCategoryInfo(@RequestParam("region") String region,
      @RequestParam("category") String categoryName) {
    Long regionId = regionService.findRegion(region);
    CategoryDetailDto response = categoryService.findDetailInfo(regionId, categoryName);
    return new ResponseEntity<>(DataResponse.of(HttpStatus.OK, "카테고리 상세 정보 조회 성공", response), HttpStatus.OK);
  }

}
