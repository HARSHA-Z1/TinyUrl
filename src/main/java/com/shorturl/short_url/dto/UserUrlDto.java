package com.shorturl.short_url.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUrlDto {
    private String originalUrl;
    private String shortUrl;
    private Long clickCount;
}
