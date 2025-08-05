package com.gymcrm.backend.service.impl;

import com.gymcrm.backend.dto.UserRequestDto;
import com.gymcrm.backend.service.EmailService;
import com.gymcrm.backend.service.UserService;
import com.gymcrm.backend.util.CommonUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserServiceImpl implements UserService {

    // Thread-safe OTP storage
    private final Map<String, String> otpStorage = new ConcurrentHashMap<>();
    private final EmailService emailService;

    public UserServiceImpl(EmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    public String registerUser(UserRequestDto dto) throws IllegalArgumentException {
        // Validate input
        if (!StringUtils.hasText(dto.getEmail())) {
            throw new IllegalArgumentException("Email cannot be empty");
        }

        // Generate and store OTP
        String otp = CommonUtils.generateOtp();
        otpStorage.put(dto.getEmail(), otp);

        // Send OTP (in production, this would be async)
        emailService.sendOtpEmail(dto.getEmail(), otp);

        return otp;
    }

    @Override
    public boolean verifyOtp(String email, String otp) throws IllegalArgumentException {
        if (!StringUtils.hasText(email) || !StringUtils.hasText(otp)) {
            throw new IllegalArgumentException("Email and OTP cannot be empty");
        }

        String storedOtp = otpStorage.get(email);
        return otp.equals(storedOtp);
    }
}