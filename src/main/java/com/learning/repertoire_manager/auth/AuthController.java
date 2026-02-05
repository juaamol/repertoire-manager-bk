package com.learning.repertoire_manager.auth;

import com.learning.repertoire_manager.auth.dto.AuthResponseDto;
import com.learning.repertoire_manager.auth.dto.RegisterRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public AuthResponseDto register(@Valid @RequestBody RegisterRequestDto request) {
        authService.register(request);
        return new AuthResponseDto("User registered successfully");
    }
}
