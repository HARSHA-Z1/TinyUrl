package com.shorturl.short_url.service;

import com.shorturl.short_url.dto.ClickEventDto;
import com.shorturl.short_url.dto.UrlResponse;
import com.shorturl.short_url.dto.UserUrlDto;
import com.shorturl.short_url.models.RedirectEvent;
import com.shorturl.short_url.models.UrlMapping;
import com.shorturl.short_url.models.User;
import com.shorturl.short_url.repsitory.RedirectEventRepository;
import com.shorturl.short_url.repsitory.UrlMappingRepository;
import com.shorturl.short_url.repsitory.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class UrlService {
    private UserRepository userRepository;
    private UrlMappingRepository urlMappingRepository;
    private RedirectEventRepository redirectEventRepository;

    public UrlResponse createShortUrl(String longUrl,Principal principal){
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: "+ principal.getName()));
        UrlMapping urlMapping = new UrlMapping();
        urlMapping.setLongUrl(longUrl);
        urlMapping.setUser(user);
        urlMapping.setCreatedAt(LocalDateTime.now());
        urlMappingRepository.save(urlMapping);

        Long sequenceId = urlMapping.getId();
        String shortUrl = UrlEncoder.encodeUrl(sequenceId);
        urlMapping.setShortUrl(shortUrl);
        urlMappingRepository.save(urlMapping);

        return new UrlResponse(shortUrl);
    }

    public String getOriginalUrl(String shortUrl) {
        UrlMapping urlMapping = urlMappingRepository.findByShortUrl(shortUrl);
        if(urlMapping == null)return null;
        urlMapping.setClickCount(urlMapping.getClickCount()+1);
        urlMappingRepository.save(urlMapping);
        RedirectEvent redirectEvent = new RedirectEvent();
        redirectEvent.setUrlMapping(urlMapping);
        redirectEvent.setLocalDateTime(LocalDateTime.now());
        redirectEventRepository.save(redirectEvent);
        return urlMapping.getLongUrl();
    }

    private UserUrlDto transformTOUserUrlDto(UrlMapping urlMapping){
        UserUrlDto userUrlDto = new UserUrlDto();
        userUrlDto.setShortUrl(urlMapping.getShortUrl());
        userUrlDto.setOriginalUrl(urlMapping.getLongUrl());
        userUrlDto.setClickCount(urlMapping.getClickCount());
        return userUrlDto;
    }

    public List<UserUrlDto> gerUserUrls(Principal principal) {
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        List<UrlMapping> urlMappings = urlMappingRepository.findbyUser(user);
        return urlMappings.stream()
                .map(this::transformTOUserUrlDto)
                .toList();
    }

    public List<ClickEventDto> getUrlAnalytics(String shortUrl, Principal principal) {
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        UrlMapping urlMapping = urlMappingRepository.findByShortUrlAndUser(shortUrl,user);
        if(urlMapping == null)return Collections.emptyList();
        return redirectEventRepository.findByUrlMapping(urlMapping).stream()
                .map(this::transformTOClickEventDto)
                        .toList();
    }

    private ClickEventDto transformTOClickEventDto(RedirectEvent redirectEvent) {
        return new ClickEventDto(redirectEvent.getLocalDateTime());
    }

}
