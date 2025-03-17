package com.example.BE_GreenHouse.service.inter;

import com.example.BE_GreenHouse.dto.Response;
import org.springframework.data.domain.Pageable;

public interface SensorService {
    Response getSensorData(String sensor, Pageable pageable);
}
