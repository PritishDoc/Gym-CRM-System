package com.gymcrm.backend.services;

import com.gymcrm.backend.dto.UserRequestDto;
public class UserService {
      String registerUser(UserRequestDto userRequestDto);
    String verifyOtp(String email, String otp);
}
