package com.sesacthon.foreco.trash.entity;

import jakarta.persistence.AttributeConverter;

public class DisposalTypeConverter implements AttributeConverter<DisposalType, Integer> {

  /**
   * Enum 값을 데이터베이스에 저장하기 위해 원하는 변환 작업 수행
   */
  @Override
  public Integer convertToDatabaseColumn(DisposalType attribute) {
    return attribute.getCode();
  }

  /**
   * 데이터베이스에서 읽은 값을 Enum으로 변환하기 위해 원하는 변환 작업 수행
   */
  @Override
  public DisposalType convertToEntityAttribute(Integer dbData) {
    return DisposalType.ofCode(dbData);
  }

}