package com.example.BE_GreenHouse.mapper;

import com.example.BE_GreenHouse.dto.DeviceDTO;
import com.example.BE_GreenHouse.model.DeviceStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DeviceMapper {
    @Mapping(source = "user.id", target = "userId")
    DeviceDTO toDeviceDTO(DeviceStatus deviceStatus);
}
