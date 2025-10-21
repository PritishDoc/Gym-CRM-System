package com.gymcrm.backend.dto;

import lombok.Data;

@Data
public class TrainerRequestDto {

    private String name;
    private String email;
    private String contactNo;
    private String gender;
    private Integer age;
    private String address;
    private Integer experienceYears;
    private String preferredTime;
    private String dateOfJoining;
}
