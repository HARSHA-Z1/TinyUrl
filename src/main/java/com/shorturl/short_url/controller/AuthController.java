package com.shorturl.short_url.controller;

import com.shorturl.short_url.dto.LoginRequest;
import com.shorturl.short_url.dto.RegisterRequest;
import com.shorturl.short_url.dto.RegisterResponse;
import com.shorturl.short_url.models.User;
import com.shorturl.short_url.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private UserService userService;

    @PostMapping("/public/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(userService.authenticateUser(loginRequest));
    }

    @PostMapping("/public/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest){
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(registerRequest.getPassword());
        user.setRole("ROLE_USER");
        RegisterResponse registerResponse = userService.registerUser(user);
        String response = registerResponse.getMessage();
        if(registerResponse.getSuccess() == true){
            return ResponseEntity.ok("User registered successfully");
        }
        return ResponseEntity.status(409).body(response);
    }
}