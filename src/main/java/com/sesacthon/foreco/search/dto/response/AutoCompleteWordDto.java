package com.sesacthon.foreco.search.dto.response;

import java.util.List;
import lombok.Getter;

@Getter
public class AutoCompleteWordDto {

    private final List<String> words;

    public AutoCompleteWordDto(List<String> words) {
        this.words = words;
    }

}
