package com.example.BE_GreenHouse.service.impl;

import com.example.BE_GreenHouse.dto.LoginRequest;
import com.example.BE_GreenHouse.dto.Response;
import com.example.BE_GreenHouse.dto.UserDTO;
import com.example.BE_GreenHouse.exception.OurException;
import com.example.BE_GreenHouse.mapper.UserMapper;
import com.example.BE_GreenHouse.model.User;
import com.example.BE_GreenHouse.repository.UserRepository;
import com.example.BE_GreenHouse.service.inter.UserService;
import com.example.BE_GreenHouse.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    final private UserRepository userRepository;
    final private UserMapper userMapper;
    final private JwtUtils jwtUtils;

    @Override
    public Response register(User user) {
        Response response = new Response();
        try {
            if (userRepository.existsByEmail(user.getEmail())){
                throw new OurException("Email already exists");
            }

            if(user.getRole() == null || user.getRole().isBlank()){
                user.setRole("USER");
            }

            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(5);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setStatus("ACTIVE");
            User user1 = userRepository.save(user);

            String token = jwtUtils.generateToken(user1);

            response.setToken(token);
            response.setUser(userMapper.toUserDTO(user1));
            response.setStatusCode(200);
            response.setMessage("User registered successfully");
        } catch (OurException e) {
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @Override
    public Response login(LoginRequest loginRequest) {
        Response response = new Response();
        try {
            User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new OurException("User not found"));
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(5);

            boolean authenticated = passwordEncoder.matches(loginRequest.getPassword(), user.getPassword());

            if(authenticated){
                user.setStatus("ACTIVE");
                String token = jwtUtils.generateToken(user);
                response.setToken(token);
                response.setUser(userMapper.toUserDTO(user));
                response.setStatusCode(200);
                response.setMessage("User logged in successfully");
            }else{
                response.setStatusCode(400);
                response.setMessage("Invalid password");
            }
        } catch (OurException e) {
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @Override
    public Response getMyInfo() {
        Response response = new Response();
        try {
            var context = SecurityContextHolder.getContext();
            String email = context.getAuthentication().getName();
            User user = userRepository.findByEmail(email).orElseThrow(() -> new OurException("User not found"));
            response.setUser(userMapper.toUserDTO(user));
            response.setStatusCode(200);
            response.setMessage("Userinfo fetched successfully");
        } catch (OurException e) {
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @Override
    public Response getAllUsers(Pageable pageable) {
        Response response = new Response();
        try {
            Page<UserDTO> userDTOPage = userRepository.findAll(pageable).map(userMapper::toUserDTO);
            response.setUsersList(userDTOPage.getContent());
            response.setCurrentPage(userDTOPage.getNumber());
            response.setTotalPages(userDTOPage.getTotalPages());
            response.setTotalElements(userDTOPage.getTotalElements());
            response.setStatusCode(200);
            response.setMessage("Users fetched successfully");
        } catch (OurException e) {
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @Override
    public Response getUserById(Long id) {
        Response response = new Response();
        try {
            User user = userRepository.findById(id).orElseThrow(() -> new OurException("User not found"));
            response.setUser(userMapper.toUserDTO(user));
            response.setStatusCode(200);
            response.setMessage("User fetched successfully");
        } catch (OurException e) {
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @Override
    public Response updateUser(UserDTO user, Long userId) {
        Response response = new Response();
        try {
            User user1 = userRepository.findById(userId).orElseThrow(() -> new OurException("User not found"));
            if (user.getEmail() != null) {
                user1.setEmail(user.getEmail());
            }
            if (user.getName() != null) {
                user1.setName(user.getName());
            }
            userRepository.save(user1);
            response.setUser(userMapper.toUserDTO(user1));
            response.setStatusCode(200);
            response.setMessage("User updated successfully");
        } catch (OurException e) {
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @Override
    public Response deleteUser(Long id) {
        Response response = new Response();
        try {
            User user = userRepository.findById(id).orElseThrow(() -> new OurException("User not found"));
            userRepository.delete(user);
            response.setStatusCode(200);
            response.setMessage("User deleted successfully");
        } catch (OurException e) {
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }


}
