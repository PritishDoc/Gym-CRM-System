package com.gymcrm.backend.dto;

import lombok.Data;

@Data
public class MemberRequestDto {

    private String name;
    private String email;
    private String contactNo;
    private String gender;
    private String membership;
    private String preferredTime;
    private Integer age;
    private String trainer;
    private String Date_of_Joining;
}
