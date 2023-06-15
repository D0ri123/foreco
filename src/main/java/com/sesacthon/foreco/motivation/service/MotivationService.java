package com.sesacthon.foreco.motivation.service;

import static com.sesacthon.global.exception.ErrorCode.MOTIVATION_NOT_FOUND;

import com.sesacthon.foreco.expectedOutput.entity.ExpectedOutput;
import com.sesacthon.foreco.expectedOutput.repository.ExpectedOutputRepository;
import com.sesacthon.foreco.motivation.dto.response.MotivationDetailDto;
import com.sesacthon.foreco.motivation.entity.Motivation;
import com.sesacthon.foreco.motivation.exception.MotivationNotFoundException;
import com.sesacthon.foreco.motivation.repository.MotivationRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MotivationService {

  private final MotivationRepository motivationRepository;
  private final ExpectedOutputRepository expectedOutputRepository;

  public MotivationDetailDto findExpectedResult(Long categoryId) {
    Motivation motivation = motivationRepository.findResultByCategoryId(categoryId)
        .orElseThrow(() -> new MotivationNotFoundException(MOTIVATION_NOT_FOUND));
    List<ExpectedOutput> outputList = expectedOutputRepository.findExpectedOutputs(motivation.getId());
    return new MotivationDetailDto(motivation, outputList);
  }
}
