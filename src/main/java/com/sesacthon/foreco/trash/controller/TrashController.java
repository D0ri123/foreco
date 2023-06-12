package com.sesacthon.foreco.trash.controller;

import com.sesacthon.foreco.trash.dto.response.TrashDetailDto;
import com.sesacthon.foreco.trash.service.TrashService;
import com.sesacthon.global.response.DataResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name="쓰레기 세부품목", description = "세부품목 관련 api")
@RestController
@RequiredArgsConstructor
public class TrashController {

  private final TrashService trashService;

  @Operation(summary = "세부 품목 쓰레기 정보", description = "지역 기반 쓰레기 배출 정보를 조회할 수 있습니다.")
  @GetMapping("/api/v1/trash")
  public ResponseEntity<DataResponse<TrashDetailDto>> getTrashInfoWithRegionCond(
      @RequestParam("region") String region,
      @RequestParam("name") String name,
      @RequestParam(value = "tab", required = false, defaultValue = "0") int tab) {
    TrashDetailDto response = trashService.findTrashesWithRegionAndName(region, name, tab);
    return new ResponseEntity<>(DataResponse.of(HttpStatus.OK, "세부 품목 쓰레기 상세 조회 성공", response), HttpStatus.OK);
  }
}
