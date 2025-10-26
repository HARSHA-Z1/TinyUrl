package com.shorturl.short_url.controller;


import com.shorturl.short_url.dto.UserUrlDto;
import com.shorturl.short_url.models.User;
import com.shorturl.short_url.service.UrlService;
import com.shorturl.short_url.service.UserDetailsImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/url")
@AllArgsConstructor
public class UrlAndAnalyticsController {
    UrlService urlService;

    @PostMapping("/shorten")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> shortenOrginalUrl(@RequestBody Map<String,String>request, Principal principal){
        String originalUrl = request.get("originalUrl");
        return ResponseEntity.ok(urlService.createShortUrl(originalUrl,principal));
    }

    @GetMapping("/myurls")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<UserUrlDto>> getUrls(Principal principal){
        return ResponseEntity.ok(urlService.gerUserUrls(principal));
    }

    @GetMapping("/events/{shortUrl}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getAnalytics(@PathVariable String shortUrl,Principal principal){
        return ResponseEntity.ok(urlService.getUrlAnalytics(shortUrl,principal));
    }

}
