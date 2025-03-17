package com.example.BE_GreenHouse.mapper;

import com.example.BE_GreenHouse.dto.UserDTO;
import com.example.BE_GreenHouse.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toUserDTO(User user);
    //User toUser(UserDTO userDTO);
    //void updateUser(@MappingTarget User user, UserDTO userDTO);
}
