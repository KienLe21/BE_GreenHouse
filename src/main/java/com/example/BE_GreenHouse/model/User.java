package com.example.BE_GreenHouse.model;

import com.zaxxer.hikari.util.ClockSource;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "email is required")
    private String email;
    @NotBlank(message = "name is required")
    private String name;
    @Size(min = 8, message = "PASSWORD_INVALID")
    private String password;
    private String status = "INACTIVE";
    private String role;

}
