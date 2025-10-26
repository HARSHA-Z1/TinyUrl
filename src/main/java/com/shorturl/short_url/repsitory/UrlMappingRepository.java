package com.shorturl.short_url.repsitory;

import com.shorturl.short_url.models.UrlMapping;
import com.shorturl.short_url.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UrlMappingRepository extends JpaRepository<UrlMapping,Long> {
    UrlMapping findByLongUrl(String longUrl);
    UrlMapping findByShortUrl(String shortUrl);
    UrlMapping findByShortUrlAndUser(String shortUrl,User user);
    List<UrlMapping> findByUser(User user);
}
