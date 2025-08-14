package com.gymcrm.backend.dto;

import lombok.Data;

@Data
public class AttendanceRequest {
    private Long userId;
    private Long gymId; // Optional if you have multiple locations
    private String checkInNotes; // Optional notes at check-in
}