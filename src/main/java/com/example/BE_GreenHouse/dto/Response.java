package com.example.BE_GreenHouse.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {
    private int statusCode;

    private String message;

    private UserDTO user;

    private List<UserDTO> usersList;

    //Pagination information
    private Integer currentPage;
    private Integer totalPages;
    private Long totalElements;
}
