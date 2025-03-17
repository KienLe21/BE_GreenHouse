package com.example.BE_GreenHouse.repository;

import com.example.BE_GreenHouse.model.SensorData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface SensorDataRepository extends JpaRepository<SensorData, Long> {
    Page<SensorData> findAllByType(String type, Pageable pageable);
}
