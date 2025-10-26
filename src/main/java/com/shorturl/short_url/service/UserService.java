package com.shorturl.short_url.service;


import com.google.common.hash.BloomFilter;
import com.shorturl.short_url.dto.LoginRequest;
import com.shorturl.short_url.dto.RegisterResponse;
import com.shorturl.short_url.models.User;
import com.shorturl.short_url.repsitory.UserRepository;
import com.shorturl.short_url.security.JwtAuthenticationResponse;
import com.shorturl.short_url.security.JwtUtils;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    private AuthenticationManager authenticationManager;
    private JwtUtils jwtUtils;
    private BloomFilter<CharSequence> userBloomFilter;

    public RegisterResponse registerUser(User user){
        String userName = user.getUsername();
        if (userBloomFilter.mightContain(userName)) {
            if (userRepository.findByUsername(userName).isPresent()) {
                return new RegisterResponse(false,"Username already exists");
            }
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            return new RegisterResponse(false,"Username already exists");
        }
        userBloomFilter.put(userName);
        return new RegisterResponse(true,"User registered successfully");
    }

    public JwtAuthenticationResponse authenticateUser(LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                        loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String jwt = jwtUtils.generateToken(userDetails);
        return new JwtAuthenticationResponse(jwt);
    }

    public User findByUsername(String name) {
        return userRepository.findByUsername(name).orElseThrow(
                () -> new UsernameNotFoundException("User not found with username: " + name)
        );
    }
}
