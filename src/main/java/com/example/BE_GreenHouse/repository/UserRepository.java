package com.example.BE_GreenHouse.repository;

import com.example.BE_GreenHouse.model.User;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByName(@NotBlank(message = "name is required") String name);

    Boolean existsByName(@NotBlank(message = "name is required") String name);

    Optional<User> findByEmail(@NotBlank(message = "email is required") String email);

}
