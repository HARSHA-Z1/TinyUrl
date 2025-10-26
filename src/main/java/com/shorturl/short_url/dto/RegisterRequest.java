package com.shorturl.short_url.dto;


import lombok.Data;

import java.util.Set;

@Data
public class RegisterRequest {
    private String username;
    private Set<String> role;
    private String password;
}