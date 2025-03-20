package com.example.BE_GreenHouse.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DeviceDTO {
    private Long id;
    private String device; // "fan", "led", "water_pump"
    private String status; // "ON" hoặc "OFF" hoặc mã màu RGB
    private LocalDateTime updatedAt;
    private Long userId;
}
