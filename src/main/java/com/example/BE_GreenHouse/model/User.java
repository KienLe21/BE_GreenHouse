package com.example.BE_GreenHouse.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;


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
    private String phoneNumber;
    private String address;
    private String status = "INACTIVE";
    private String role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<DeviceStatus> deviceStatusList;

}
