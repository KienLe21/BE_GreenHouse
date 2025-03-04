package com.example.BE_GreenHouse.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserDTO {
    private String email;
    private String name;
    private String status;
    private String role;
}
