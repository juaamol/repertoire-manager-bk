package com.learning.repertoire_manager.user.dto;

import lombok.Data;

@Data
public class LoginRequestDto {
    private String email;
    private String password;
}
