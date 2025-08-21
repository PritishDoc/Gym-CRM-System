// AttendanceConfirmationRequest.java
package com.gymcrm.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class AttendanceConfirmationRequest {

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Check-in time is required")
    private String checkInTime;

    // Constructors, getters, and setters
    public AttendanceConfirmationRequest() {}

    public AttendanceConfirmationRequest(String email, String name, String checkInTime) {
        this.email = email;
        this.name = name;
        this.checkInTime = checkInTime;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getCheckInTime() { return checkInTime; }
    public void setCheckInTime(String checkInTime) { this.checkInTime = checkInTime; }
}