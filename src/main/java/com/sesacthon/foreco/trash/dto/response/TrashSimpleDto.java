package com.sesacthon.foreco.trash.dto.response;

import com.sesacthon.foreco.trash.entity.Trash;
import lombok.Getter;

/**
 * 쓰레기 간단 정보 응답 Dto
 */
@Getter
public class TrashSimpleDto {

  private final Long id;
  private final String name;

  public TrashSimpleDto(Trash trash) {
    this.id = trash.getId();
    this.name = trash.getTrashName();
  }

  public TrashSimpleDto(Long id, String name) {
    this.id = id;
    this.name = name;
  }
}
