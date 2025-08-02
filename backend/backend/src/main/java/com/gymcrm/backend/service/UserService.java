package com.gymcrm.backend.service;

import com.gymcrm.backend.dto.UserRequestDto;

public interface UserService{
    String registerUsers(UserRequestDto userRequestDto);
  String verifyOtp(String email, String otp);

    String registerUser(com.gymcrm.backend.service.UserRequestDto dto);
}