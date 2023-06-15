package com.sesacthon.foreco.motivation.dto.response;

import com.sesacthon.foreco.expectedOutput.dto.response.ExpectedOutputDto;
import com.sesacthon.foreco.expectedOutput.entity.ExpectedOutput;
import com.sesacthon.foreco.motivation.entity.Motivation;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class MotivationDetailDto {

  private final List<ProcessDto> recyclingProcess = new ArrayList<>();
  private final List<ExpectedOutputDto> expectedOutputs;
  private final List<ProcessDto> ifYouDoNot = new ArrayList<>();

  public MotivationDetailDto(Motivation motivation, List<ExpectedOutput> outputList) {
    Map<String, String> processMap = parsingProcess(motivation.getRecyclingProc());
    Map<String, String> ifNotMap = parsingProcess(motivation.getIfNot());

    for (Map.Entry<String, String> entry : processMap.entrySet()) {
      this.recyclingProcess.add(new ProcessDto(entry.getKey(), entry.getValue()));
    }

    this.expectedOutputs = outputList.stream()
        .map(ExpectedOutputDto::new)
        .collect(Collectors.toList());

    for (Map.Entry<String, String> entry : ifNotMap.entrySet()) {
      this.ifYouDoNot.add(new ProcessDto(entry.getKey(), entry.getValue()));
    }
  }

  private Map<String, String> parsingProcess(String process) {
    Map<String, String> result = new LinkedHashMap<>();
    //-는 url에 자주 포함되기 때문에 구분자 변경
    String[] splits = process.split(">");
    //#는 과정에서 제목,글 구분자
    for (String s : splits) {
      String[] info = s.split("#");
      result.put(info[0], info[1]);
    }
    return result;
  }

}
