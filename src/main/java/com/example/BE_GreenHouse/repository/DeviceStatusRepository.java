package com.example.BE_GreenHouse.repository;

import com.example.BE_GreenHouse.model.DeviceStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.time.LocalDateTime;
import java.util.Optional;

public interface DeviceStatusRepository extends JpaRepository<DeviceStatus, Long> {
    Optional<DeviceStatus> findTopByDeviceOrderByUpdatedAtDesc(String device);
    Page<DeviceStatus> findAllByDeviceOrderByUpdatedAtDesc(String device, Pageable pageable);
    Page<DeviceStatus> findAllByDeviceAndUserIdOrderByUpdatedAtDesc(String device, Long userId, Pageable pageable);
    @Query("SELECT CASE WHEN COUNT(d) > 0 THEN TRUE ELSE FALSE END FROM DeviceStatus d WHERE d.device = :device AND ABS(TIMESTAMPDIFF(SECOND, d.updatedAt, :updatedAt)) < 1")
    boolean existsByDeviceAndUpdatedAtWithinOneSecond(@Param("device") String device, @Param("updatedAt") LocalDateTime updatedAt);
}
