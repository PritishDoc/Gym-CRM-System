package com.gymcrm.backend.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import org.springframework.stereotype.Component;

@Autowired
private JavaMailSender mailSender;

public void sendOtp(String toEmail, String otp) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(toEmail);
    message.setSubject("Your OTP Code");
    message.setText("Your OTP is: " + otp);
    mailSender.send(message);
}

