package com.gymcrm.backend.service;

public interface EmailService {
    void sendOtpEmail(String toEmail, String otp);
}