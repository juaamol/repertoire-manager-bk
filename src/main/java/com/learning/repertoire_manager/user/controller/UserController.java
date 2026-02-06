package com.learning.repertoire_manager.user.controller;

import com.learning.repertoire_manager.security.JwtService;
import com.learning.repertoire_manager.user.dto.AuthResponseDto;
import com.learning.repertoire_manager.user.dto.LoginRequestDto;
import com.learning.repertoire_manager.user.dto.LoginResponseDto;
import com.learning.repertoire_manager.user.dto.RegisterRequestDto;
import com.learning.repertoire_manager.user.model.User;
import com.learning.repertoire_manager.user.repository.UserRepository;
import com.learning.repertoire_manager.user.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtService.generateToken(user.getId(), user.getEmail(), user.getRole().name());
        return new LoginResponseDto(token);
    }

    @PostMapping("/register")
    public AuthResponseDto register(@Valid @RequestBody RegisterRequestDto request) {
        userService.register(request);
        return new AuthResponseDto("User registered successfully");
    }
}
