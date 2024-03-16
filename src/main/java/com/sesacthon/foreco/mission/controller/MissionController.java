package com.sesacthon.foreco.mission.controller;

import com.sesacthon.foreco.jwt.dto.SessionUser;
import com.sesacthon.foreco.mission.dto.QuizMissionDto;
import com.sesacthon.foreco.mission.service.MissionService;
import com.sesacthon.foreco.mission.dto.MissionDetailDto;
import com.sesacthon.foreco.mission.dto.MissionDto;
import com.sesacthon.global.response.DataResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import com.sesacthon.foreco.mission.dto.MissionResultDto;
import com.sesacthon.foreco.mission.dto.MissionResultInfoDto;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name="미션 관련 기능", description = "미션 관련 api")
@RestController
@RequiredArgsConstructor
public class MissionController {
  private final MissionService missionService;

  @Operation(summary = "쓰레기 맞추기 미션 요청 api", description = "조각난 쓰레기 사진을 보고 어떤 쓰레기인지 맞추는 미션 api")
  @GetMapping("/api/v1/mission/{id}")
  public ResponseEntity<DataResponse<QuizMissionDto>> getMission(
      @PathVariable(name = "id") Long id
  ) {

    QuizMissionDto response = missionService.getQuizMission(id);
    return new ResponseEntity<>(DataResponse.of(HttpStatus.OK, "퀴즈 미션 요청 성공", response), HttpStatus.OK);
  }

  @Operation(summary = "미션 목록 조회 api", description = "미션 목록을 조회하는 api 입니다.")
  @PreAuthorize("isAuthenticated()")
  @GetMapping("/api/v1/mission")
  public ResponseEntity<DataResponse<MissionDto>> getMissions(
      @Parameter(description = "kind는 미션의 카테고리를 나타냅니다. 사용하지 않을 시 \"WIZ\"가 기본적으로 사용되며 \"ETC\"를 조회할 시 필수로 사용해야합니다. difficulty는 난이도를 나타냅니다. \"LOW\", \"MIDDLE\",\"HIGH\"중 하나를 요청보내야합니다.")
      @RequestParam(name = "kind", required = true, defaultValue = "WIZ") String kind,
      @RequestParam(name = "difficulty", required = false) String difficulty,
      @AuthenticationPrincipal SessionUser sessionUser
  ) {

    if (difficulty == null) {
      List<MissionDetailDto> missions = missionService.findMissionsWithKind(kind, sessionUser.getUuid());
      MissionDto getAllMission = new MissionDto(missions);
      return new ResponseEntity<>(
          DataResponse.of(HttpStatus.OK, "미션 목록 조회 성공", getAllMission), HttpStatus.OK);
    }

    List<MissionDetailDto> missionsWithKindAndDifficulty =
        missionService.findMissionsWithKindAndDifficulty(kind, difficulty, sessionUser.getUuid());
    MissionDto getAllMissionWithCond = new MissionDto(missionsWithKindAndDifficulty);
    return new ResponseEntity<>(
        DataResponse.of(HttpStatus.OK, "미션 목록 조회 성공", getAllMissionWithCond), HttpStatus.OK);
  }

  @Operation(summary = "요청 결과 반영 api", description = "미션 수행이후 결과를 반영하고, 변경된 포인트 이력을 보여줍니다.")
  @PreAuthorize("isAuthenticated()")
  @PostMapping("/api/v1/mission/result")
  public ResponseEntity<DataResponse<MissionResultInfoDto>> updateMissionResult(
      @RequestBody MissionResultDto missionResultDto,
      @AuthenticationPrincipal SessionUser sessionUser
  ) {
    MissionResultInfoDto response = missionService.recordHistory(missionResultDto, sessionUser.getUuid());
    return new ResponseEntity<>(DataResponse.of(HttpStatus.OK, "결과 반영 성공", response), HttpStatus.OK);
  }
}

