package com.gymcrm.backend.service;

import com.gymcrm.backend.dto.AuthResponse;
import com.gymcrm.backend.dto.LoginRequest;
import com.gymcrm.backend.dto.RegisterRequest;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse authenticate(LoginRequest request);
}