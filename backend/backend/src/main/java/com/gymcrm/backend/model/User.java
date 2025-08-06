package com.gymcrm.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullname;
    private String email;
    private String contactNo;
    private String gender;
    private String membership;
    private String preferredTime;
    private String password;
    private String otp;
    private boolean verified;
    private boolean active;
    private String createdAt;
}