package com.example.BE_GreenHouse.service.impl;

import com.example.BE_GreenHouse.dto.Response;
import com.example.BE_GreenHouse.model.DeviceStatus;
import com.example.BE_GreenHouse.repository.DeviceStatusRepository;
import com.example.BE_GreenHouse.service.MqttPublisherService;
import com.example.BE_GreenHouse.service.inter.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class DeviceServiceImpl implements DeviceService {
    private final DeviceStatusRepository deviceStatusRepository;

    private final MqttPublisherService mqttPublisherService;

    @Override
    public Response getDeviceStatus(String device) {
        Response response = new Response();
        try {
            DeviceStatus deviceStatus = deviceStatusRepository.findTopByDeviceOrderByUpdatedAtDesc(device).orElse(null);
            response.setDeviceStatus(deviceStatus);
            response.setMessage("Get device status successfully");
            response.setStatusCode(200);
        } catch (Exception e) {
            response.setMessage("Get device status failed");
            response.setStatusCode(500);
        }
        return response;
    }

    @Override
    public Response updateDeviceStatus(String device, String status) {
        Response response = new Response();
        try {
            if (!status.equalsIgnoreCase("ON") && !status.equalsIgnoreCase("OFF") && !isValidRGB(status)) {
                throw new Exception("Invalid status");
            }

            mqttPublisherService.sendCommand(device, status);
            DeviceStatus deviceStatus = new DeviceStatus();
            deviceStatus.setDevice(device);
            deviceStatus.setStatus(status);
            deviceStatus.setUpdatedAt(java.time.LocalDateTime.now());
            deviceStatusRepository.save(deviceStatus);

            response.setDeviceStatus(deviceStatus);
            response.setMessage("Update device status successfully");
            response.setStatusCode(200);
        } catch (Exception e) {
            response.setMessage("Update device status failed");
            response.setStatusCode(500);
        }
        return response;
    }

    private boolean isValidRGB(String status) {
        String rgbPattern = "^#([A-Fa-f0-9]{6})$";
        return Pattern.matches(rgbPattern, status);
    }
}
