package com.gymcrm.backend.service.impl;

import com.gymcrm.backend.service.EmailService;
import com.gymcrm.backend.util.EmailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private final EmailSender emailSender;

    public EmailServiceImpl(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Override
    public void sendOtpEmail(String toEmail, String otp) {
        String subject = "Your OTP for Gym CRM Registration";
        String body = "Your OTP is: " + otp + "\n\nThis OTP is valid for 10 minutes.";

        emailSender.sendEmail(toEmail, subject, body);
    }
}