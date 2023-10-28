package com.sesacthon.foreco.mission.controller;


import com.sesacthon.foreco.mission.dto.QuizMissionDto;
import com.sesacthon.foreco.mission.service.MissionService;
import com.sesacthon.global.response.DataResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MissionController {
  private final MissionService missionService;

  @Operation(summary = "쓰레기 맞추기 미션 요청 api", description = "조각난 쓰레기 사진을 보고 어떤 쓰레기인지 맞추는 미션 api")
  @GetMapping("api/v1/mission/{id}")
  public ResponseEntity<DataResponse<QuizMissionDto>> getMission1(
      @PathVariable(name = "id") Long id) {

    QuizMissionDto response = missionService.getQuizMission(id);
    return new ResponseEntity<>(DataResponse.of(HttpStatus.OK, "퀴즈 미션 요청 성공", response),
        HttpStatus.OK);
  }
}
