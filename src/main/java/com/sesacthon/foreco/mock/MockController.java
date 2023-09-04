package com.sesacthon.foreco.mock;


import com.sesacthon.foreco.disposal.dto.response.DisposalInfoDto;
import com.sesacthon.foreco.disposal.dto.response.TodayDisposableCategoriesDto;
import com.sesacthon.foreco.mock.dto.RelevantTrashDto;
import com.sesacthon.foreco.mock.dto.RelevantTrashesDto;
import com.sesacthon.foreco.mock.dto.SearchedTrashDto;
import com.sesacthon.foreco.mock.dto.SearchedTrashesDto;
import com.sesacthon.foreco.mock.dto.TrashDetailDto;
import com.sesacthon.global.response.DataResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Mock API", description = "임시 api")
@RestController
@RequiredArgsConstructor
public class MockController {

  @Operation(summary = "금일 배출 가능한 품목 제공 API", description = "금일 배출가능한 품목을 조회합니다. 현재는 요일(day)에 관계없이 동일한 값을 반환합니다!")
  @GetMapping("mock/api/v1/category")
  public ResponseEntity<DataResponse<TodayDisposableCategoriesDto>> getTodayDisposableCategories(
      @Parameter(description = "\"월요일\", \"화요일\" 같은 형식으로 입력받을 예정입니다.") @RequestParam("day") String day) {
    TodayDisposableCategoriesDto response = new TodayDisposableCategoriesDto(
        Arrays.asList("플라스틱", "종이"));
    return new ResponseEntity<>(DataResponse.of(HttpStatus.OK, "금일 배출 가능한 카테고리 조회 성공", response),
        HttpStatus.OK);
  }


  @Operation(summary = "쓰레기 검색(조회) API", description = "메인 화면에서 검색을 통해 상세조회가 가능한 쓰레기의 정보를 조회합니다. 현재는 검색어(keyword)에 상관없이 고정된 값을 반환합니다!")
  @GetMapping("mock/api/v1/trash")
  public ResponseEntity<DataResponse<SearchedTrashesDto>> getTrashesBySearch(
      @Parameter(description = "검색할 키워드를 입력해주세요") @RequestParam("keyword") String keyword) {

    SearchedTrashesDto response = new SearchedTrashesDto(Arrays.asList(
        new SearchedTrashDto(1L, "검색된 페트병",
            "https://postfiles.pstatic.net/MjAyMzAzMDlfMTU1/MDAxNjc4MzQ0OTk4MTMx.bsYYQx3KsbEmFEKxhmXXvH1Vk-dyLjn2-ECxIaKyJdMg.j_V4Zxtoi8ZDVfmORtO7pzshskoycWx3TFwf9zCeeAkg.JPEG.mha0715/IMG%EF%BC%BF20230309%EF%BC%BF122138%EF%BC%BF513.jpg?type=w966"),
        new SearchedTrashDto(2L, "검색된 계란껍데기",
            "https://postfiles.pstatic.net/MjAyMzAzMDlfMTU1/MDAxNjc4MzQ0OTk4MTMx.bsYYQx3KsbEmFEKxhmXXvH1Vk-dyLjn2-ECxIaKyJdMg.j_V4Zxtoi8ZDVfmORtO7pzshskoycWx3TFwf9zCeeAkg.JPEG.mha0715/IMG%EF%BC%BF20230309%EF%BC%BF122138%EF%BC%BF513.jpg?type=w966")));
    return new ResponseEntity<>(DataResponse.of(HttpStatus.OK, "쓰레기 검색결과 조회 성공", response),
        HttpStatus.OK);
  }

  @Operation(summary = "쓰레기 상세 조회시 필요한, 관련된 쓰레기 정보 조회 API", description = "쓰레기 상세조회 화면에서 제공되야할 관련된 쓰레기들 입니다. 저번주 회의 끝나고 이야기했던 부분으로 따로 분리했습니다.")
  @GetMapping("mock/api/v1/trash/relation")
  public ResponseEntity<DataResponse<RelevantTrashesDto>> getRelevantTrashes(
      @Parameter(description = "조회할 쓰레기 id(숫자)") @RequestParam("id") Long id) {
    RelevantTrashesDto response = new RelevantTrashesDto(Arrays.asList(
        new RelevantTrashDto(3L, "관련된 페트병",
            "https://static.wikia.nocookie.net/shinchan/images/3/36/%EC%8B%A0%EC%A7%B1%EC%95%84.jpg/revision/latest?cb=20131021074814&path-prefix=ko"),
        new RelevantTrashDto(4L, "관련된 계란껍데기",
            "https://static.wikia.nocookie.net/shinchan/images/3/36/%EC%8B%A0%EC%A7%B1%EC%95%84.jpg/revision/latest?cb=20131021074814&path-prefix=ko")));
    return new ResponseEntity<>(DataResponse.of(HttpStatus.OK, "관련된(추천) 쓰레기 정보 조회 성공", response),
        HttpStatus.OK);
  }

  @Operation(summary = "쓰레기 상세 조회 API", description = "쓰레기 상세조회 API 입니다.")
  @GetMapping("mock/api/v1/trash/detail")
  public ResponseEntity<DataResponse<TrashDetailDto>> getTrashDetails(
      @Parameter(description = "id == 1 일때 detailType=MAP /id == 2 일때 detailType = BIG / 나머지는 detailType = BASIC이 반환되게 해놨습니다.") @RequestParam("id") Long id) {
    //지도 Map
    if (id == 1L) {
      TrashDetailDto response = new TrashDetailDto(1L, DetailType.MAP, "지도/폐건전지",
          "폐건전지는 전용 수거함에 버려주세요. 가까운 곳에 폐건전지 수거함이 없다면, 주민센터를 활용하여 버릴수 있어요.", new DisposalInfoDto(),
          Arrays.asList(" "),"https://static.wikia.nocookie.net/shinchan/images/3/36/%EC%8B%A0%EC%A7%B1%EC%95%84.jpg/revision/latest?cb=20131021074814&path-prefix=ko");
      return new ResponseEntity<>(
          DataResponse.of(HttpStatus.OK, "detailType = map, 쓰레기 상세조회 성공", response), HttpStatus.OK);
    }
    //폐기물 Big
    if (id == 2L) {
      TrashDetailDto response = new TrashDetailDto(2L, DetailType.BIG, "폐기/소파",
          "소파는 지자체 신고후, 스티커 또는 예약 번호를 적어서 대형 생활 폐기물로 신고배출해주세요.", new DisposalInfoDto(),
          Arrays.asList("집밖으로 배출시에는 등받이, 쿠션등이 떨어지지 않도록 테이프, 끈으로 고정해주세요",
              "대형 생활폐기물 신고 및 수거는 유료이며, 가구의 크기에 따라 2,000원 ~ 10,000원 가량 부과됩니다.",
              "시터커를 발급받지 않고 길거리, 야산, 쓰레기 수거장소에 몰래 버릴경우 수거가 되지 않으며, 무단 투기로 간주되어 폐기물관리법에 의해 100만원 이하의 과태료가 부과됩니다."),"https://static.wikia.nocookie.net/shinchan/images/3/36/%EC%8B%A0%EC%A7%B1%EC%95%84.jpg/revision/latest?cb=20131021074814&path-prefix=ko");
      return new ResponseEntity<>(
          DataResponse.of(HttpStatus.OK, "detailType = map, 쓰레기 상세조회 성공", response), HttpStatus.OK);
    }
    //일반 Basic
    TrashDetailDto response = new TrashDetailDto(3L, DetailType.BASIC, "일반/계란껍데기",
        "계란의 단단한 껍질은 종량제 쓰레기 봉투에 담아 배출해주세요.", new DisposalInfoDto(),
        Arrays.asList("날달걀, 삶은 달걍 등이 상했다면 껍데기를 까서 계란 속은 음식물 쓰레기로 버리고,단단한 껍질만 일반쓰레기(종량제 봉투)로 버려주세요.",
            "껍질 속을 물로 한번 헹궈서 버리면 쓰레기 봉투 안에서 썩거나 벌레가 생기는 걸 막을 수 있어서 도움이 될 수 있어요."),"https://static.wikia.nocookie.net/shinchan/images/3/36/%EC%8B%A0%EC%A7%B1%EC%95%84.jpg/revision/latest?cb=20131021074814&path-prefix=ko");
    return new ResponseEntity<>(
        DataResponse.of(HttpStatus.OK, "detailType=map, 쓰레기 상세조회 성공", response), HttpStatus.OK);


  }
}
