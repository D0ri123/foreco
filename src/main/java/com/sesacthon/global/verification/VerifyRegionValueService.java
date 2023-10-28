package com.sesacthon.global.verification;

import static com.sesacthon.global.exception.ErrorCode.NOT_VALID_REGION_TYPE;

import com.sesacthon.foreco.disposal.controller.region.exception.NotValidRegionTypeException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VerifyRegionValueService {

  private boolean checkLastCharacter(String[] value) {
    return value[0].charAt(value[0].length()-1) == '시'
        && value[1].charAt(value[1].length()-1) == '구'
        && value[2].charAt(value[2].length()-1) == '동';
  }

  public String[] checkParam(String region) {
    String[] regionVal = region.split(" ");
    if(!(regionVal.length == 3 && checkLastCharacter(regionVal))) {
      throw new NotValidRegionTypeException(NOT_VALID_REGION_TYPE);
    }
    return regionVal;
  }
}
