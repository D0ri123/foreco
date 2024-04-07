package com.sesacthon.foreco.search.config;

import net.okihouse.autocomplete.implement.AutocompleteServiceImpl;
import net.okihouse.autocomplete.key.AutocompleteKeyServiceImpl;
import net.okihouse.autocomplete.repository.AutocompleteKeyRepository;
import net.okihouse.autocomplete.repository.AutocompleteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
public class AutoCompleteConfig {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Bean(name = {"autocompleteKeyRepository", "keyRepository"})
    public AutocompleteKeyRepository keyRepository() {
        return new AutocompleteKeyServiceImpl(stringRedisTemplate);
    }

    @Bean(name = {"autocompleteRepository"})
    public AutocompleteRepository autocompleteRepository(AutocompleteKeyRepository autocompleteKeyRepository) {
        return new AutocompleteServiceImpl(stringRedisTemplate, autocompleteKeyRepository);
    }

}
