package com.gymcrm.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String fullname;
    private String email;
    private String contactNo;
    private String gender;
    private String membership;
    private String preferredTime;
    private boolean verified;
    private String createdAt;
}