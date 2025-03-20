package com.example.BE_GreenHouse.controller;

import com.example.BE_GreenHouse.dto.DeviceDTO;
import com.example.BE_GreenHouse.dto.Response;
import com.example.BE_GreenHouse.service.inter.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @GetMapping("/get-all-device-status/{device}")
    public ResponseEntity<Response> getAllDeviceStatus(@PathVariable String device, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page, size);
        Response response = deviceService.getAllDeviceStatus(device, pageable);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/get-all-device-status-by-user-id/{device}")
    public ResponseEntity<Response> getAllDeviceStatusByUserId(@PathVariable String device, @RequestParam Long userId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page, size);
        Response response = deviceService.getAllDeviceStatusByUserId(device, userId, pageable);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping("/update-device-status")
    public ResponseEntity<Response> updateDeviceStatus(@RequestBody DeviceDTO deviceDTO){
        Response response = deviceService.updateDeviceStatus(deviceDTO);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
