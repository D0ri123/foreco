package com.sesacthon.foreco.search.dto.response;

import java.util.List;
import lombok.Getter;

@Getter
public class RandomTrashesDto {
    private final List<RandomTrashKeywordDto> recommend;

    public RandomTrashesDto(List<RandomTrashKeywordDto> recommend) {
        this.recommend = recommend;
    }


}
