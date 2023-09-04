package com.sesacthon.foreco.trash.service;

import com.sesacthon.foreco.category.entity.Trash;
import com.sesacthon.foreco.category.repository.TrashRepository;
import com.sesacthon.foreco.disposal.repository.DisposalRepository;

import com.sesacthon.foreco.trash.dto.RelevantTrashesDto;
import com.sesacthon.foreco.trash.entity.TrashInfo;
import com.sesacthon.foreco.trash.exception.RelatedTrashNotFoundException;
import com.sesacthon.foreco.trash.exception.TrashNotFoundException;
import com.sesacthon.foreco.trash.repository.TrashInfoRepository;

import com.sesacthon.global.exception.ErrorCode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrashService {

  private final TrashInfoRepository trashInfoRepository;
  private final DisposalRepository disposalRepository;
  private final TrashRepository trashRepository;


  public RelevantTrashesDto getRelevantTrashes(Long id, Long regionId) {
    //현재 쓰레기를 가져옴
    Trash currentTrash = trashRepository.findById(id).orElseThrow(() -> new TrashNotFoundException(
        ErrorCode.TRASH_NOT_FOUND));

    //현재 쓰레기의 카테고리(부모)를 구함.
    Trash categoryTrash = currentTrash.getParentTrash();
    //부모 카테고리에 속해있는 연관쓰레기(자식)를 구함
    List<Trash> childTrashes = categoryTrash.getChildTrashes();
    childTrashes.remove(currentTrash); //현재 조회중인 trash 제외

    //아무런 연관쓰레기가 없을 시, 에러처리
    if (childTrashes.isEmpty()) {
      throw new RelatedTrashNotFoundException(ErrorCode.RELATED_TRASH_NOT_FOUND);
    }

    List<TrashInfo> trashInfos = new ArrayList<>();
    for (Trash childTrash : childTrashes) {
      Optional<TrashInfo> trashInfo = trashInfoRepository.findByTrashIdAndRegionId(
          childTrash.getId(), regionId);
      trashInfo.ifPresent(trashInfos::add);
    }

    return new RelevantTrashesDto(trashInfos);
  }

//  public TrashDetailDto getTrashDetail(Long id, Long regionId) {
//    Optional<TrashInfo> trashInfo = trashInfoRepository.findByIdAndRegionId(id,regionId);
//    log.info("trashInfo{}",trashInfo.get().getId());
//    return null;
//  }
}
