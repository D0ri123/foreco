package com.sesacthon.foreco.trash.entity;

import static com.sesacthon.global.exception.ErrorCode.DISPOSAL_TYPE_ENUM_NOT_SUPPORTED;

import com.sesacthon.foreco.trash.exception.TypeNotSupportedException;
import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DisposalType {

  RECYCLABLE(1),
  NON_RECYCLABLE(2),
  BULKY(3),
  SPECIAL(4);

  private final Integer code;

  public static DisposalType ofCode(Integer code) {
    return Arrays.stream(DisposalType.values())
        .filter(v -> v.getCode().equals(code))
        .findAny()
        .orElseThrow(() -> new TypeNotSupportedException(DISPOSAL_TYPE_ENUM_NOT_SUPPORTED));
  }

}