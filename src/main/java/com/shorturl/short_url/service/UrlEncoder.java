package com.shorturl.short_url.service;

public class UrlEncoder {
    private static final String ALPHABETS = "abcdefghijklmnopqrstuvwxyz0123456789";

    public static String encodeUrl(Long number) {
        StringBuilder shortUrl = new StringBuilder();
        Long length = 36L;
        if(number == 0)return "a";
        while (number != 0) {
            long mod = number % length;
            shortUrl.append(ALPHABETS.charAt((int)mod));
            number = number / 36;
        }
        return shortUrl.reverse().toString();
    }
}
