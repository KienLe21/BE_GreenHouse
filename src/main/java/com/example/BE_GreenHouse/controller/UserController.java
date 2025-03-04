package com.example.BE_GreenHouse.controller;

import com.example.BE_GreenHouse.dto.LoginRequest;
import com.example.BE_GreenHouse.dto.Response;
import com.example.BE_GreenHouse.dto.UserDTO;
import com.example.BE_GreenHouse.model.User;
import com.example.BE_GreenHouse.service.inter.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    final private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Response> register(@RequestBody User user){
        Response response = userService.register(user);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody LoginRequest loginRequest){
        Response response = userService.login(loginRequest);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("get-all-users")
    public ResponseEntity<Response> getAllUsers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page, size);
        Response response = userService.getAllUsers(pageable);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("get-user-by-id/{id}")
    public ResponseEntity<Response> getUserById(@PathVariable Long id){
        Response response = userService.getUserById(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PutMapping("update-user/{id}")
    public ResponseEntity<Response> updateUser(@RequestBody UserDTO user, @PathVariable Long id){
        Response response = userService.updateUser(user, id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("delete-user/{id}")
    public ResponseEntity<Response> deleteUser(@PathVariable Long id){
        Response response = userService.deleteUser(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
