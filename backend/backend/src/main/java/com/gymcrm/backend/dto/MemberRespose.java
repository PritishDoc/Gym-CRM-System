package com.gymcrm.backend.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberRespose {

    private Long id;
    private String name;
    private String email;
    private String contactNo;
    private String gender;
    private String membership;
    private String preferredTime;
    private boolean verified;
    private String Date_of_Joining;
    private Integer age;
    private String trainer;
}
