package com.example.BE_GreenHouse.service.inter;

import com.example.BE_GreenHouse.dto.Response;

public interface DeviceService {
    Response getDeviceStatus(String device);
    Response updateDeviceStatus(String device, String status);
}
