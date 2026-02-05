package com.learning.repertoire_manager.auth.dto;

import lombok.Data;

@Data
public class LoginRequestDto {
    private String email;
    private String password;
}
