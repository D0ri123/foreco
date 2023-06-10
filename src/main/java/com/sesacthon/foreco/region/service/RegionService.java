package com.sesacthon.foreco.region.service;

import static com.sesacthon.global.exception.ErrorCode.REGION_NOT_FOUND;

import com.sesacthon.foreco.region.exception.RegionNotFoundException;
import com.sesacthon.foreco.region.repository.RegionRepository;
import com.sesacthon.global.verification.VerifyRegionValueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegionService {

  private final RegionRepository regionRepository;
  private final VerifyRegionValueService verifyRegionValueService;

  public Long findRegion(String region) {
    String[] regionVal = verifyRegionValueService.checkParam(region);
    return regionRepository.findRegionByCityAndGuAndDong(regionVal[0], regionVal[1], regionVal[2])
        .orElseThrow(() -> new RegionNotFoundException(REGION_NOT_FOUND));
  }
}
