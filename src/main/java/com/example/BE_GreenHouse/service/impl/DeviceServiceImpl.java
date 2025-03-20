package com.example.BE_GreenHouse.service.impl;

import com.example.BE_GreenHouse.dto.DeviceDTO;
import com.example.BE_GreenHouse.dto.Response;
import com.example.BE_GreenHouse.exception.OurException;
import com.example.BE_GreenHouse.mapper.DeviceMapper;
import com.example.BE_GreenHouse.model.DeviceStatus;
import com.example.BE_GreenHouse.model.User;
import com.example.BE_GreenHouse.repository.DeviceStatusRepository;
import com.example.BE_GreenHouse.repository.UserRepository;
import com.example.BE_GreenHouse.service.MqttPublisherService;
import com.example.BE_GreenHouse.service.inter.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class DeviceServiceImpl implements DeviceService {
    private final DeviceStatusRepository deviceStatusRepository;

    private final UserRepository userRepository;

    private final MqttPublisherService mqttPublisherService;

    private final DeviceMapper deviceMapper;

    @Override
    public Response getDeviceStatus(String device) {
        Response response = new Response();
        try {
            DeviceStatus deviceStatus = deviceStatusRepository.findTopByDeviceOrderByUpdatedAtDesc(device).orElse(null);
            response.setDevice(deviceMapper.toDeviceDTO(deviceStatus));
            response.setMessage("Get device status successfully");
            response.setStatusCode(200);
        } catch (Exception e) {
            response.setMessage("Get device status failed");
            response.setStatusCode(500);
        }
        return response;
    }

    @Override
    public Response getAllDeviceStatus(String device, Pageable pageable) {
        Response response = new Response();
        try {
            Page<DeviceDTO> deviceDTOList = deviceStatusRepository.findAllByDeviceOrderByUpdatedAtDesc(device, pageable).map(deviceMapper::toDeviceDTO);
            response.setDeviceList(deviceDTOList.getContent());
            response.setCurrentPage(deviceDTOList.getNumber());
            response.setTotalPages(deviceDTOList.getTotalPages());
            response.setTotalElements(deviceDTOList.getTotalElements());
            response.setMessage("Get all device status successfully");
            response.setStatusCode(200);
        } catch (Exception e) {
            response.setMessage("Get all device status failed");
            response.setStatusCode(500);
        }
        return response;
    }

    @Override
    public Response getAllDeviceStatusByUserId(String device, Long userId, Pageable pageable) {
        Response response = new Response();
        try {
            Page<DeviceDTO> deviceDTOList = deviceStatusRepository.findAllByDeviceAndUserIdOrderByUpdatedAtDesc(device, userId, pageable).map(deviceMapper::toDeviceDTO);
            response.setDeviceList(deviceDTOList.getContent());
            response.setCurrentPage(deviceDTOList.getNumber());
            response.setTotalPages(deviceDTOList.getTotalPages());
            response.setTotalElements(deviceDTOList.getTotalElements());
            response.setMessage("Get all device status successfully");
            response.setStatusCode(200);
        } catch (Exception e) {
            response.setMessage("Get all device status failed");
            response.setStatusCode(500);
        }
        return response;
    }

    @Override
    public Response updateDeviceStatus(DeviceDTO deviceDTO) {
        Response response = new Response();
        try {
            String device = deviceDTO.getDevice();
            String status = deviceDTO.getStatus();

            var context = SecurityContextHolder.getContext();
            String email = context.getAuthentication().getName();
            User user = userRepository.findByEmail(email).orElseThrow(() -> new OurException("User not found"));

            if (!status.equalsIgnoreCase("ON") && !status.equalsIgnoreCase("OFF") && !isValidRGB(status)) {
                throw new Exception("Invalid status");
            }

            mqttPublisherService.sendCommand(device, status);
            DeviceStatus deviceStatus = new DeviceStatus();
            deviceStatus.setDevice(device);
            deviceStatus.setStatus(status);
            deviceStatus.setUpdatedAt(java.time.LocalDateTime.now());
            deviceStatus.setUser(user);
            deviceStatusRepository.save(deviceStatus);

            response.setDevice(deviceMapper.toDeviceDTO(deviceStatus));
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
