package com.example.BE_GreenHouse.dto;

import com.example.BE_GreenHouse.model.DeviceStatus;
import com.example.BE_GreenHouse.model.SensorData;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {
    private int statusCode;

    private String message;

    private String token;

    private UserDTO user;

    private List<UserDTO> usersList;

    private DeviceDTO device;

    private List<DeviceDTO> deviceList;

    private List<SensorData> sensorDataList;

    //Pagination information
    private Integer currentPage;
    private Integer totalPages;
    private Long totalElements;
}
