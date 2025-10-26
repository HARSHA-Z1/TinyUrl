package com.shorturl.short_url.component;
import com.google.common.hash.BloomFilter;
import com.shorturl.short_url.repsitory.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
@AllArgsConstructor
public class BloomFilterInitializer {
    private UserRepository userRepository;
    private BloomFilter<CharSequence> userBloomFilter;

    @PostConstruct
    public void init() {
        userRepository.findAll()
                .forEach(user -> userBloomFilter.put(user.getUsername()));
    }
}