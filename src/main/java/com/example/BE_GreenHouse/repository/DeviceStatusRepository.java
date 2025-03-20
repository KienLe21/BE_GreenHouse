package com.example.BE_GreenHouse.repository;

import com.example.BE_GreenHouse.model.DeviceStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface DeviceStatusRepository extends JpaRepository<DeviceStatus, Long> {
    Optional<DeviceStatus> findTopByDeviceOrderByUpdatedAtDesc(String device);
    Page<DeviceStatus> findAllByDeviceOrderByUpdatedAtDesc(String device, Pageable pageable);
    Page<DeviceStatus> findAllByDeviceAndUserIdOrderByUpdatedAtDesc(String device, Long userId, Pageable pageable);
}
