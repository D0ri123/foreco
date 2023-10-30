package com.sesacthon.foreco.mission.controller;


import com.sesacthon.foreco.mission.dto.QuizMissionDto;
import com.sesacthon.foreco.mission.service.MissionService;
import com.sesacthon.foreco.mock.mission.dto.MissionDetailDto;
import com.sesacthon.foreco.mock.mission.dto.MissionDto;
import com.sesacthon.global.response.DataResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MissionController {
  private final MissionService missionService;
  private static final UUID MEMBER_ID = UUID.fromString("667c59d0-0524-47fb-ab5a-effdaa62b598");

  @Operation(summary = "쓰레기 맞추기 미션 요청 api", description = "조각난 쓰레기 사진을 보고 어떤 쓰레기인지 맞추는 미션 api")
  @GetMapping("api/v1/mission/{id}")
  public ResponseEntity<DataResponse<QuizMissionDto>> getMission(
      @PathVariable(name = "id") Long id) {

    QuizMissionDto response = missionService.getQuizMission(id);
    return new ResponseEntity<>(DataResponse.of(HttpStatus.OK, "퀴즈 미션 요청 성공", response),
        HttpStatus.OK);
  }

  /**
   * 미션 목록을 조회합니다.
   *
   * @param kind 미션 종류
   * @param difficulty 미션 난이도
   * @return MissionDto 미션 목록들을 담은 Dto
   */
  @Operation(summary = "미션 목록 조회 api", description = "미션 목록을 조회하는 api 입니다.")
  @GetMapping("/api/v1/mission")
  public ResponseEntity<DataResponse<MissionDto>> getMissions(
      @Parameter(description = "kind는 미션의 카테고리를 나타냅니다. 사용하지 않을 시 \"WIZ\"가 기본적으로 사용되며 \"ETC\"를 조회할 시 필수로 사용해야합니다. difficulty는 난이도를 나타냅니다. \"LOW\", \"MIDDLE\",\"HIGH\"중 하나를 요청보내야합니다.")
      @RequestParam(name = "kind", required = true, defaultValue = "WIZ") String kind,
      @RequestParam(name = "difficulty", required = false) String difficulty) {

    if (difficulty == null) {
      List<MissionDetailDto> missions = missionService.findMissionsWithKind(kind, MEMBER_ID);
      MissionDto getAllMission = new MissionDto(missions);
      return new ResponseEntity<>(DataResponse.of(HttpStatus.OK, "미션 목록 조회 성공", getAllMission), HttpStatus.OK);
    }

    List<MissionDetailDto> missionsWithKindAndDifficulty =
        missionService.findMissionsWithKindAndDifficulty(kind, difficulty, MEMBER_ID);
    MissionDto getAllMissionWithCond = new MissionDto(missionsWithKindAndDifficulty);
    return new ResponseEntity<>(DataResponse.of(HttpStatus.OK, "미션 목록 조회 성공", getAllMissionWithCond), HttpStatus.OK);
  }


}
