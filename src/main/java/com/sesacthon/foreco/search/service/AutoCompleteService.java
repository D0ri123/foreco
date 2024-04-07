package com.sesacthon.foreco.search.service;

import com.sesacthon.foreco.search.dto.response.AutoCompleteWordDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import net.okihouse.autocomplete.repository.AutocompleteRepository;
import net.okihouse.autocomplete.vo.AutocompleteData;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AutoCompleteService {

    private final AutocompleteRepository autocompleteRepository;

    public AutoCompleteWordDto getAutoCompleteWords(String word) {
        autocompleteRepository.add(word);
        List<AutocompleteData> complete = autocompleteRepository.complete(word);

        List<String> result = complete.stream()
            .map(AutocompleteData::getValue)
            .collect(Collectors.toList());
        return new AutoCompleteWordDto(result);
    }
}
