package com.sesacthon.foreco.search.controller;

import com.sesacthon.foreco.search.dto.response.AutoCompleteWordDto;
import com.sesacthon.foreco.search.dto.response.RandomTrashesDto;
import com.sesacthon.foreco.search.service.AutoCompleteService;
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

@Tag(name="검색 관련 기능", description = "검색 관련 api")
@RestController
@RequiredArgsConstructor
public class SearchController {

    private final TrashService trashService;
    private final AutoCompleteService autoCompleteService;

    @Operation(summary = "쓰레기 랜덤 키워드 6개 반환")
    @GetMapping("/api/v1/search/random")
    public ResponseEntity<DataResponse<RandomTrashesDto>> getMission() {
        RandomTrashesDto randomTrashes = trashService.getRandomTrashes();
        return new ResponseEntity<>(
            DataResponse.of(HttpStatus.OK, "쓰레기 랜덤 키워드 반환 성공", randomTrashes), HttpStatus.OK);
    }

    @Operation(summary = "검색어 자동완성 결과 반환")
    @GetMapping("/api/v1/search/auto-complete")
    public ResponseEntity<DataResponse<AutoCompleteWordDto>> getAutoCompleteWords(@RequestParam("word") String word) {
        AutoCompleteWordDto autoCompleteWords = autoCompleteService.getAutoCompleteWords(word);
        return new ResponseEntity<>(
            DataResponse.of(HttpStatus.OK, "자동완성 결과 반환 성공", autoCompleteWords), HttpStatus.OK);
    }

}
