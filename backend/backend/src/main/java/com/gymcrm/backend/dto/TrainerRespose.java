package com.gymcrm.backend.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TrainerRespose {

    private Long id;
    private String name;
    private String email;
    private String contactNo;
    private String gender;
    private Integer age;
    private String address;
    private Integer experienceYears;
    private String preferredTime;
    private boolean verified;
    private String dateOfJoining;
}
