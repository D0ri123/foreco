package com.sesacthon.foreco.mission.dto;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@RequiredArgsConstructor
public class ImageDivisionResponseDto {

  private List<String> images;

}
