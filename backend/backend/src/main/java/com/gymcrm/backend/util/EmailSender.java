package com.gymcrm.backend.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import org.springframework.stereotype.Component;
@ComponentScan
public class EmailSender{
    @Autowired
    private javaMailSender mailSender;
    public void sendOtp(String to, String otp){
        SimpleMailMessage message=new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Your OTP - GYM CRM");
        message.setText("Your OTP is: "+otp);
        mailSender.send(message);
    }
}

