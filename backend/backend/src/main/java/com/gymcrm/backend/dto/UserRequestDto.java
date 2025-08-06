package com.gymcrm.backend.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {
    private String fullname;
    private String email;
    private String contactNo;
    private String gender;
    private String membership;
    private String preferredTime;
    private String password; // Added for registration
}