package com.sesacthon.foreco.disposal.service;

import com.sesacthon.foreco.disposal.dto.response.DisposableCategoryDto;
import com.sesacthon.foreco.disposal.entity.Disposal;
import com.sesacthon.foreco.disposal.repository.DisposalRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DisposalService {

  private final DisposalRepository disposalRepository;

  /**
   * 지역 정보, 요일과 일치한 카테고리 데이터를 가져온다.
   * @param regionId 사용자의 지역 정보
   */
  public List<DisposableCategoryDto> findCategoriesWithRegionAndDate(Long regionId) {
    String today = LocalDate.now().getDayOfWeek().toString();
    List<Disposal> disposalList = disposalRepository.findDisposableList(regionId, today);
    return disposalList.stream()
        .map(disposal -> new DisposableCategoryDto(disposal.getCategory().getId(), disposal.getCategory().getTrashType()))
        .collect(Collectors.toList());

  }


}
