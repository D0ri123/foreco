package com.sesacthon.infra.dynamodb.repository;

import com.sesacthon.infra.dynamodb.Image;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class SpringDataImageRepository {

  private final SpringDataDynamoImageRepository springDataDynamoImageRepository;

  public void make(String category, String imgUrl) {
    springDataDynamoImageRepository.save(new Image(category, imgUrl));
  }


}
