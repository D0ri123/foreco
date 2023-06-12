package com.sesacthon.foreco.trash.service;

import static com.sesacthon.global.exception.ErrorCode.TRASH_NOT_FOUND;

import com.sesacthon.foreco.disposal.entity.Disposal;
import com.sesacthon.foreco.disposal.repository.DisposalRepository;
import com.sesacthon.foreco.region.service.RegionService;
import com.sesacthon.foreco.trash.dto.response.PlasticDetailDto;
import com.sesacthon.foreco.trash.dto.response.TrashDetailDto;
import com.sesacthon.foreco.trash.entity.Trash;
import com.sesacthon.foreco.trash.exception.TrashNotFoundException;
import com.sesacthon.foreco.trash.repository.TrashRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrashService {

  private final TrashRepository trashRepository;
  private final DisposalRepository disposalRepository;
  private final RegionService regionService;


  public TrashDetailDto findTrashesWithRegionAndName(String region, String name, int tab) {
    Long regionId = regionService.findRegion(region);
    Trash trash = trashRepository.findTrashByRegionAndName(regionId, name, tab)
        .orElseThrow(() -> new TrashNotFoundException(TRASH_NOT_FOUND));
    List<Disposal> disposals = disposalRepository.findDisposalInfo(regionId, trash.getCategory().getId());
    return new TrashDetailDto(trash, disposals, trash.getExamples());
  }

  /**
   * 플라스틱의 경우 쓰레기 품목 중, 유일하게 색깔 여부를 고려하기 때문에
   * 플라스틱 정보만을 위한 서비스 메서드 생성
   * @param region 사용자의 지역
   */
  public PlasticDetailDto findPlasticInfoWithRegion(String region) {
    Long regionId = regionService.findRegion(region);
    List<Trash> plastics = trashRepository.findPlasticByRegion(regionId);
    List<Disposal> disposals = disposalRepository.findDisposalInfo(regionId, plastics.get(0).getCategory().getId());
    return new PlasticDetailDto(plastics, disposals);
  }

}
