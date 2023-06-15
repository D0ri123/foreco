package com.sesacthon.foreco.motivation.controller;

import com.sesacthon.foreco.motivation.dto.response.MotivationDetailDto;
import com.sesacthon.foreco.motivation.service.MotivationService;
import com.sesacthon.global.response.DataResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name="재활용 동기부여", description = "동기부여 관련 api")
@RestController
@RequiredArgsConstructor
public class MotivationController {
  private final MotivationService motivationService;

  @Operation(summary = "동기부여 정보", description = "재활용 가능한 품목에 대해서 동기부여(재활용 과정, 기대 결과물..) 제공을 합니다.")
  @GetMapping("/api/v1/motive")
  public ResponseEntity<DataResponse<MotivationDetailDto>> getMotive(@RequestParam("categoryId")Long categoryId) {
    MotivationDetailDto expectedResult = motivationService.findExpectedResult(categoryId);
    return new ResponseEntity<>(DataResponse.of(HttpStatus.OK, "동기부여 정보 조회 성공", expectedResult), HttpStatus.OK);
  }

}
