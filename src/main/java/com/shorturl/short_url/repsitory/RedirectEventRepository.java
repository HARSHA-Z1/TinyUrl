package com.shorturl.short_url.repsitory;

import com.shorturl.short_url.models.RedirectEvent;
import com.shorturl.short_url.models.UrlMapping;
import com.shorturl.short_url.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RedirectEventRepository extends JpaRepository<RedirectEvent,Long> {
    List<RedirectEvent> findByUrlMapping(UrlMapping urlMapping);
}


