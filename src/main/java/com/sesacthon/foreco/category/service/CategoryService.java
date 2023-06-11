package com.sesacthon.foreco.category.service;

import static com.sesacthon.global.exception.ErrorCode.CATEGORY_NOT_FOUND;

import com.sesacthon.foreco.category.dto.response.CategoryDetailDto;
import com.sesacthon.foreco.category.entity.Category;
import com.sesacthon.foreco.category.exception.CategoryNotFoundException;
import com.sesacthon.foreco.category.repository.CategoryRepository;
import com.sesacthon.foreco.disposal.entity.Disposal;
import com.sesacthon.foreco.disposal.repository.DisposalRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

  private final CategoryRepository categoryRepository;
  private final DisposalRepository disposalRepository;

  public CategoryDetailDto findDetailInfo(Long regionId, String categoryName) {
    Category category = categoryRepository.findCategoryInfo(regionId, categoryName)
        .orElseThrow(() -> new CategoryNotFoundException(CATEGORY_NOT_FOUND));

    List<Disposal> disposalInfo = disposalRepository.findDisposalInfo(regionId, category.getId());
    return new CategoryDetailDto(category, disposalInfo);
  }
}
