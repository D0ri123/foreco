package com.sesacthon.foreco.trash.service;

import com.sesacthon.foreco.category.entity.RegionCategory;
import com.sesacthon.foreco.category.entity.Trash;
import com.sesacthon.foreco.category.repository.RegionCategoryRepository;
import com.sesacthon.foreco.category.repository.TrashRepository;
import com.sesacthon.foreco.disposal.dto.response.DisposalInfoDto;
import com.sesacthon.foreco.disposal.entity.Disposal;
import com.sesacthon.foreco.search.dto.response.RandomTrashKeywordDto;
import com.sesacthon.foreco.search.dto.response.RandomTrashesDto;
import com.sesacthon.foreco.trash.dto.RelevantTrashDto;
import com.sesacthon.foreco.trash.dto.RelevantTrashesDto;
import com.sesacthon.foreco.trash.dto.SearchedTrashDto;
import com.sesacthon.foreco.trash.dto.SearchedTrashesDto;
import com.sesacthon.foreco.trash.dto.TrashDetailDto;
import com.sesacthon.foreco.trash.entity.TrashInfo;
import com.sesacthon.foreco.trash.exception.RelatedTrashNotFoundException;
import com.sesacthon.foreco.trash.exception.DisposalNotFoundException;
import com.sesacthon.foreco.trash.exception.TrashNotFoundException;
import com.sesacthon.foreco.trash.repository.TrashInfoRepository;
import com.sesacthon.global.exception.ErrorCode;
import com.sesacthon.infra.s3.service.S3Downloader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrashService {

  private final TrashInfoRepository trashInfoRepository;
  private final TrashRepository trashRepository;
  private final RegionCategoryRepository regionCategoryRepository;
  private final S3Downloader s3Downloader;


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

    List<RelevantTrashDto> trashes = childTrashes.stream()
        .map((trash) -> {
          String iconUrl = s3Downloader.getIconUrl(trash.getTrashIcon().getIconFile());
          return new RelevantTrashDto(trash.getId(), trash.getName(), iconUrl);
        })
        .collect(Collectors.toList());

    return new RelevantTrashesDto(trashes);
  }

  public TrashDetailDto getTrashDetail(Long id, Long regionId) {
    //id로 trash 객체를 가져옴
    Trash trash = trashRepository.findById(id)
        .orElseThrow(() -> new TrashNotFoundException(ErrorCode.TRASH_NOT_FOUND));
    log.info("trash{}:", trash);
    //trashInfo가져옴
    TrashInfo trashInfo = trashInfoRepository.findByTrashIdAndRegionId(trash.getId(), regionId)
        .orElseThrow(() -> new TrashNotFoundException(ErrorCode.TRASH_NOT_FOUND));
    log.info("trashInfo{}:", trashInfo);

    //배출 정보를 가져옴
    List<Disposal> disposals = getDisposals(regionId, trash);
    log.info("disposal{}:", disposals);

    TrashDetailDto trashDetailDto = TrashDetailDto.builder()
        .id(trash.getId())
        .detailType(trash.getViewType()).name(trash.getName()).disposalMethod(trashInfo.getMethod())
        .disposalInfoDto(new DisposalInfoDto(disposals))
        .remark(trashInfo.getRemarks().stream().map(remark -> remark.getDescription()).toList())
        .iconUrl(s3Downloader.getIconUrl(trash.getTrashIcon().getIconFile()))
        .build();

    return trashDetailDto;

  }

  private List<Disposal> getDisposals(Long regionId, Trash trash) {
    Long parentTrashId = trash.getParentTrash().getId();
    RegionCategory regionCategory = regionCategoryRepository.findByTrashIdAndRegionId(parentTrashId,
        regionId).orElseThrow(() -> new DisposalNotFoundException(ErrorCode.DISPOSAL_NOT_FOUND));
    List<Disposal> disposals = regionCategory.getDisposals();
    return disposals;
  }

  //위치 기반으로 쓰레기 키워드 검색 결과를 가져와야 한다.
  public SearchedTrashesDto searchTrashWithKeyword(Long regionId, String keyword) {
    //1. regionId를 가지고, categoryList들을 가져온다.
    List<Long> categoryIds = regionCategoryRepository.findCategoryIdsByRegionId(regionId);
    List<Trash> allTrashes = new ArrayList<>();

    //2. categoryId 중, 해당 Id를 외래키로 가지는 쓰레기 목록중에서 keyword를 포함하는 결과를 가져온다.
    for (Long category : categoryIds) {
      List<Trash> trashes = trashRepository.searchTrashWithKeyword(category, keyword);
      allTrashes.addAll(trashes);
    }

    List<SearchedTrashDto> result = allTrashes.stream()
        .map((trash) -> {
          String iconUrl = s3Downloader.getIconUrl(trash.getTrashIcon().getIconFile());
          return new SearchedTrashDto(trash.getId(), trash.getName(), iconUrl);
        })
        .collect(Collectors.toList());

    return new SearchedTrashesDto(result);
  }

  public RandomTrashesDto getRandomTrashes() {
    //1. 대분류 쓰레기가 아닌 쓰레기 리스트를 가져온다.
    List<Trash> trashes = trashRepository.findTrashes();

    // 쓰레기 리스트를 돌면서 dto로 변환한다.
    List<RandomTrashKeywordDto> randomTrashes = trashes.stream()
        .map(RandomTrashKeywordDto::new)
        .collect(Collectors.toList());

    return new RandomTrashesDto(randomTrashes);
  }
}
