package com.gymcrm.backend.dto;

import lombok.Data;

@Data
public class UserRequestDto {
    private String fullname;
    private String email;
    private String contactNo;
    private String gender;
    private String membership;
    private String preferredTime;
}