package com.shorturl.short_url.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterResponse {
    private Boolean success;
    private String message;
}
