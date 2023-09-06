package com.sesacthon.foreco.trash.dto;

import java.util.List;
import lombok.Getter;

@Getter
public class RelevantTrashesDto {

  private final List<RelevantTrashDto> recommendTrashes;

//  public RelevantTrashesDto(List<TrashInfo> trashInfos){
//    this.recommendTrashes = trashInfos.stream().map((trashInfo)->{
//      Trash trash = trashInfo.getTrash();
//
//     return new RelevantTrashDto(trash.getId(), trash.getName(), trash.getTrashIcon());
//    }).collect(Collectors.toList());
//  }

  public RelevantTrashesDto(List<RelevantTrashDto> recommendTrashes) {
    this.recommendTrashes = recommendTrashes;
  }
}
