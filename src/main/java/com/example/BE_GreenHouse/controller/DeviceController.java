package com.example.BE_GreenHouse.controller;

import com.example.BE_GreenHouse.dto.Response;
import com.example.BE_GreenHouse.service.inter.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/device")
@RequiredArgsConstructor
public class DeviceController {
    private final DeviceService deviceService;

    @GetMapping("/get-device-status/{device}")
    public ResponseEntity<Response> getDeviceStatus(@PathVariable String device){
        Response response = deviceService.getDeviceStatus(device);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping("/update-device-status/{device}/{status}")
    public ResponseEntity<Response> updateDeviceStatus(@PathVariable String device, @PathVariable String status){
        Response response = deviceService.updateDeviceStatus(device, status);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
