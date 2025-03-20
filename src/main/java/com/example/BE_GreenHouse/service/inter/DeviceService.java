package com.example.BE_GreenHouse.service.inter;

import com.example.BE_GreenHouse.dto.DeviceDTO;
import com.example.BE_GreenHouse.dto.Response;

import org.springframework.data.domain.Pageable;

public interface DeviceService {
    Response getDeviceStatus(String device);
    Response getAllDeviceStatus(String device, Pageable pageable);
    Response getAllDeviceStatusByUserId(String device, Long userId, Pageable pageable);
    Response updateDeviceStatus(DeviceDTO deviceDTO);
}
