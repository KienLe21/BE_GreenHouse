package com.example.BE_GreenHouse.service.impl;

import com.example.BE_GreenHouse.dto.Response;
import com.example.BE_GreenHouse.model.SensorData;
import com.example.BE_GreenHouse.repository.SensorDataRepository;
import com.example.BE_GreenHouse.service.inter.SensorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SensorServiceImpl implements SensorService {
    private final SensorDataRepository sensorDataRepository;

    @Override
    public Response getSensorData(String sensor, Pageable pageable) {
        Response response = new Response();
        try {
            Page<SensorData> sensorDataList = sensorDataRepository.findAllByType(sensor, pageable);

            response.setSensorDataList(sensorDataList.getContent());
            response.setCurrentPage(sensorDataList.getNumber());
            response.setTotalPages(sensorDataList.getTotalPages());
            response.setTotalElements(sensorDataList.getTotalElements());
            response.setMessage("Get sensor data successfully");
            response.setStatusCode(200);
        } catch (Exception e) {
            response.setMessage("Get sensor data failed");
            response.setStatusCode(500);
        }
        return response;
    }
}
