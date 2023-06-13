package com.sesacthon.foreco.motivation.service;

import static com.sesacthon.global.exception.ErrorCode.MOTIVATION_NOT_FOUND;

import com.sesacthon.foreco.expectedOutput.entity.ExpectedOutput;
import com.sesacthon.foreco.expectedOutput.repository.ExpectedOutputRepository;
import com.sesacthon.foreco.motivation.dto.response.MotivationDetailDto;
import com.sesacthon.foreco.motivation.entity.Motivation;
import com.sesacthon.foreco.motivation.exception.MotivationNotFoundException;
import com.sesacthon.foreco.motivation.repository.MotivationRepository;
import com.sesacthon.foreco.trash.repository.TrashRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MotivationService {

  private final MotivationRepository motivationRepository;
  private final TrashRepository trashRepository;
  private final ExpectedOutputRepository expectedOutputRepository;

  public MotivationDetailDto findExpectedResult(Long trashId) {
    //1. 해당 쓰레기의 카테고리 Id를 찾는다.
    Long categoryId = trashRepository.findCategoryId(trashId);
    //2. 카테고리 Id에 해당하는 동기부여 결과를 가져온다.
    Motivation motivation = motivationRepository.findResultByCategoryId(categoryId)
        .orElseThrow(() -> new MotivationNotFoundException(MOTIVATION_NOT_FOUND));
    //3. motivation Id를 가진 사진 리스트 결과를 가져온다.
    List<ExpectedOutput> outputList = expectedOutputRepository.findExpectedOutputs(motivation.getId());

    return new MotivationDetailDto(motivation, outputList);
  }
}
