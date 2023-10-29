package com.sesacthon.infra.feign.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ImageDivisionResponseDto {

  @Schema(description = "분할된 이미지 url")
  List<String> images;

}
