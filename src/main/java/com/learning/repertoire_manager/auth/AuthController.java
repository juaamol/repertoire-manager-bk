package com.learning.repertoire_manager.auth;

import com.learning.repertoire_manager.auth.dto.AuthResponseDto;
import com.learning.repertoire_manager.auth.dto.LoginRequestDto;
import com.learning.repertoire_manager.auth.dto.LoginResponseDto;
import com.learning.repertoire_manager.auth.dto.RegisterRequestDto;
import com.learning.repertoire_manager.model.User;
import com.learning.repertoire_manager.repository.UserRepository;
import com.learning.repertoire_manager.security.JwtService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
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

        String token = jwtService.generateToken(user.getId(), user.getEmail());
        return new LoginResponseDto(token);
    }

    @PostMapping("/register")
    public AuthResponseDto register(@Valid @RequestBody RegisterRequestDto request) {
        authService.register(request);
        return new AuthResponseDto("User registered successfully");
    }
}
