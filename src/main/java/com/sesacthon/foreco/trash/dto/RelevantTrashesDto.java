package com.sesacthon.foreco.trash.dto;

import com.sesacthon.foreco.category.entity.Trash;
import com.sesacthon.foreco.trash.entity.TrashInfo;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class RelevantTrashesDto {

  private final List<RelevantTrashDto> recommendTrashes;


  public RelevantTrashesDto(List<TrashInfo> trashInfos){
    this.recommendTrashes = trashInfos.stream().map((trashInfo)->{
      Trash trash = trashInfo.getTrash();
     return new RelevantTrashDto(trash.getId(), trash.getName(), trash.getTrashIcon());
    }).collect(Collectors.toList());
  }
}
