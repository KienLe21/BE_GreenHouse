package com.example.BE_GreenHouse.service.inter;

import com.example.BE_GreenHouse.dto.LoginRequest;
import com.example.BE_GreenHouse.dto.Response;
import com.example.BE_GreenHouse.dto.UserDTO;
import com.example.BE_GreenHouse.model.User;
import org.springframework.data.domain.Pageable;


public interface UserService {
    Response register(User user);
    Response login(LoginRequest loginRequest);
    Response getAllUsers(Pageable pageable);
    Response getUserById(Long id);
    Response updateUser(UserDTO user, Long userId);
    Response deleteUser(Long id);
}
