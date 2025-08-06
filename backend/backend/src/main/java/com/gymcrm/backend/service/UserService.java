package com.gymcrm.backend.service;

import com.gymcrm.backend.dto.UserRequestDto;
import com.gymcrm.backend.dto.UserResponse;

import java.util.List;

public interface UserService {
    String registerUser(UserRequestDto dto);
    String verifyOtp(String email, String otp);

    UserResponse getUserByEmail(String email);

    UserResponse updateUser(Long id, UserRequestDto dto);

    void deleteUser(Long id);

    List<UserResponse> getAllUsers();
}