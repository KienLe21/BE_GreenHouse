package com.example.BE_GreenHouse.controller;

import com.example.BE_GreenHouse.dto.Response;
import com.example.BE_GreenHouse.service.inter.SensorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sensor")
@RequiredArgsConstructor
public class SensorController {
    private final SensorService sensorService;

    @GetMapping("/get-sensor-data/{sensor}")
    public ResponseEntity<Response> getSensorData(@PathVariable String sensor, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page, size);
        Response response = sensorService.getSensorData(sensor, pageable);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
