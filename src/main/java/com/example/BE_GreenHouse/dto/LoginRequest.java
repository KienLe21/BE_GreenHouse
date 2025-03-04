package com.example.BE_GreenHouse.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
