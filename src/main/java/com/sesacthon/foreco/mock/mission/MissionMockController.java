package com.sesacthon.foreco.mock.mission;

import com.sesacthon.foreco.mission.entity.Difficulty;
import com.sesacthon.foreco.mission.entity.Kind;
import com.sesacthon.foreco.mock.mission.dto.DashboardDto;
import com.sesacthon.foreco.mock.mission.dto.MissionDetailDto;
import com.sesacthon.foreco.mock.mission.dto.MissionDto;
import com.sesacthon.foreco.mock.mission.dto.MissionInfo;
import com.sesacthon.foreco.mock.mission.dto.MissionResultDto;
import com.sesacthon.foreco.mock.mission.dto.MissionResultInfoDto;
import com.sesacthon.foreco.mock.mission.dto.QuizMissionAnswer;
import com.sesacthon.foreco.mock.mission.dto.QuizMissionChoice;
import com.sesacthon.foreco.mock.mission.dto.QuizMissionDto;
import com.sesacthon.global.response.DataResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Mission Mock API", description = "임시 api")
@RestController
@RequiredArgsConstructor
public class MissionMockController {

  @Operation(summary = "미션 목록 조회 api", description = "미션 목록을 조회하는 api 입니다.")
  @GetMapping("mock/api/v1/mission")
  public ResponseEntity<DataResponse<MissionDto>> getMission(
      @Parameter(description = "kind는 미션의 카테고리를 나타냅니다. 사용하지 않을 시 \"WIZ\"가 기본적으로 사용되며 \"ETC\"를 조회할 시 필수로 사용해야합니다. difficulty는 난이도를 나타냅니다. \"LOW\", \"MIDDLE\",\"HIGH\"중 하나를 요청보내야합니다.")
      @RequestParam(name = "kind", required = true, defaultValue = "WIZ") String kind,
      @RequestParam(name = "difficulty", required = false) String difficulty) {
    MissionDetailDto mission1 = MissionDetailDto.builder().id(1L).kind(Kind.WIZ).title("쓰레기 퍼즐 맞추기")
        .description("조각난 쓰레기 퍼즐을 보고배출방법 맞추기").difficulty(Difficulty.LOW).rewardPoint(2000L)
        .totalCount(100L).totalNumberOfParticipating(10L).personalParticipatingCount(0L)
        .personalCount(5L).iconUrl("url")
        .build();
    MissionDetailDto mission2 = MissionDetailDto.builder().id(2L).kind(Kind.WIZ).title("쓰레기 멀리 던지기")
        .description("쓰레기를 멀리 던진다.").difficulty(Difficulty.MIDDLE).rewardPoint(2000L)
        .totalCount(100L)
        .totalNumberOfParticipating(10L).personalParticipatingCount(1L).personalCount(5L)
        .iconUrl("url")
        .build();
    List<MissionDetailDto> missions = new ArrayList<>();
    missions.add(mission1);
    missions.add(mission2);
    MissionDto response = new MissionDto(missions);
    return new ResponseEntity<>(DataResponse.of(HttpStatus.OK, "미션 조회 성공", response),
        HttpStatus.OK);
  }




  @Operation(summary = "쓰레기 맞추기 미션 요청 api", description = "조각난 쓰레기 사진을 보고 어떤 쓰레기인지 맞추는 미션 api")
  @GetMapping("mock/api/v1/mission/{id}")
  public ResponseEntity<DataResponse<QuizMissionDto>> getMission1(
      @PathVariable(name = "id") Long id) {

    //실제 구현시, s3에 업로드하여 url로 반환해야함. -> 요청마다 url생성을 위해서 일회성 이미지를 업로드 하는 것이 맞는 건가...?
    List<String> images = Arrays.asList("url1", "url2", "url3", "url4");
    List<QuizMissionChoice> choices = new ArrayList<>();
    choices.add(new QuizMissionChoice(1L, "소파"));
    choices.add(new QuizMissionChoice(2L, "의류"));
    choices.add(new QuizMissionChoice(3L, "형광등"));
    choices.add(new QuizMissionChoice(4L, "소주병"));
    QuizMissionAnswer answer = new QuizMissionAnswer(1L, "소파", "소파이미지url", "지자체에 신고 후 버려주세요.");
    MissionInfo missionInfo = new MissionInfo(2000L, "쓰레기 퍼즐 맞추기", "아래에 조각난 쓰레기는 무엇일까요?", 1L, 5L);

    QuizMissionDto response = new QuizMissionDto(missionInfo, images, choices, answer);

    return new ResponseEntity<>(DataResponse.of(HttpStatus.OK, "퀴즈 미션 요청 성공", response),
        HttpStatus.OK);
  }

  @Operation(summary = "요청 결과 반영 api", description = "미션 수행이후 결과를 반영하고, 변경된 포인트 이력을 보여줍니다.")
  @PostMapping("mock/api/v1/mission/result")
  public ResponseEntity<DataResponse<MissionResultInfoDto>> updateMissionResult(
      @RequestBody MissionResultDto missionResultDto) {
    //TODO 실제 api 구현시 contextHolder에서 값을 가져온 후  user의 id를 활용해야함
    //유저아이디를 조회후, rewardPoint반영
    //성공했으면, 성공을 반영
    //실패했으면, 실패를 반영.
    //사용자
    MissionResultInfoDto response = new MissionResultInfoDto(5L, 10L);
    return new ResponseEntity<>(DataResponse.of(HttpStatus.OK, "결과 반영 성공", response),
        HttpStatus.OK);

  }
}
