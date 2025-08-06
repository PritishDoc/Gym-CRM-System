package com.gymcrm.backend.service;

import com.gymcrm.backend.dto.UserRequestDto;

public interface UserService {
    String registerUser(UserRequestDto dto);
    String verifyOtp(String email, String otp);
}