package com.gymcrm.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data                       // generates getters, setters, toString, equals, hashCode
@NoArgsConstructor          // generates default constructor
@AllArgsConstructor         // generates constructor with all fields
public class AttendanceDto {

    private Long userId;
    private String userName;
    private LocalDate date;
    private boolean present;
    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;

}
