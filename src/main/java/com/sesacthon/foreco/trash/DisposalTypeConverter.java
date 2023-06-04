package com.sesacthon.foreco.trash;

import com.sesacthon.foreco.trash.entity.DisposalType;
import jakarta.persistence.AttributeConverter;

public class DisposalTypeConverter implements AttributeConverter<DisposalType, Integer> {

  @Override
  public Integer convertToDatabaseColumn(DisposalType attribute) {
    return null;
  }

  @Override
  public DisposalType convertToEntityAttribute(Integer dbData) {
    return null;
  }

}
