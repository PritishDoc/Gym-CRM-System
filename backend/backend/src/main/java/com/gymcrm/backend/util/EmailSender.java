package com.gymcrm.backend.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import org.springframework.stereotype.Component;

@Component
public class EmailSender {
    public void sendOtp(String toEmail, String otp) {
        // Simulate sending email
        System.out.println("Sending OTP " + otp + " to " + toEmail);
    }
}
