package com.sesacthon.foreco.category.controller;

import com.sesacthon.foreco.category.dto.response.CategoryDetailDto;
import com.sesacthon.foreco.category.service.CategoryService;
import com.sesacthon.foreco.region.service.RegionService;
import com.sesacthon.global.response.DataResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CategoryController {

  private final CategoryService categoryService;
  private final RegionService regionService;

  @GetMapping("/api/v1/category")
  public ResponseEntity<DataResponse<CategoryDetailDto>> getCategoryInfo(@RequestParam("region") String region,
      @RequestParam("category") String categoryName) {
    Long regionId = regionService.findRegion(region);
    CategoryDetailDto response = categoryService.findDetailInfo(regionId, categoryName);
    return new ResponseEntity<>(DataResponse.of(HttpStatus.OK, "카테고리 상세 정보 조회 성공", response), HttpStatus.OK);
  }

}
