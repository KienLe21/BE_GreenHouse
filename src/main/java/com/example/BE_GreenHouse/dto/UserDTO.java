package com.example.BE_GreenHouse.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String email;
    private String name;
    private String phoneNumber;
    private String address;
    private String status;
    private String role;
}
