package com.sesacthon.foreco.search.dto.response;

import com.sesacthon.foreco.category.entity.Trash;
import lombok.Getter;

@Getter
public class RandomTrashKeywordDto {
    private final Long id;
    private final String name;

    public RandomTrashKeywordDto(Trash trash) {
        this.id = trash.getId();
        this.name = trash.getName();
    }
}
