package com.sesacthon.infra.dynamodb.service;

import com.sesacthon.infra.dynamodb.repository.SpringDataImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreService {

  private final SpringDataImageRepository imageRepository;

  public void saveImg(String category, String imgUrl) {
    imageRepository.make(category, imgUrl);
  }

}
