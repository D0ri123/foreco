package com.sesacthon.infra.dynamodb.controller;

import com.sesacthon.infra.dynamodb.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ImgController {

  private final StoreService storeService;

  @PostMapping(value = "/api/v1/dynamodb/img" )
  public String uploadEventImg (
      @RequestParam("category") String trashCg, @RequestParam("imgUrl") String url) {
      storeService.saveImg(trashCg, url);
      return "ok";
  }

}
