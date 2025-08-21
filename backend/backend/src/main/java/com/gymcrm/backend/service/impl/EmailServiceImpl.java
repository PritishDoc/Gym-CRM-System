package com.gymcrm.backend.service.impl;

import com.gymcrm.backend.service.EmailService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        javaMailSender.send(message);
    }

    @Override
    public void sendWelcomeEmail(String email, String name) {
        String subject = "Welcome to Our Gym!";
        String body = String.format(
                "Dear %s,\n\nWelcome to our gym family! We're excited to have you on board.\n\n" +
                        "Your account has been successfully created. You can now access all our facilities " +
                        "and services.\n\nBest regards,\nGym Management Team",
                name
        );
        sendEmail(email, subject, body);
    }

    @Override
    public void sendPaymentReminder(String email, String name, Double amount) {
        String subject = "Payment Reminder - Gym Membership";
        String body = String.format(
                "Dear %s,\n\nThis is a friendly reminder that your gym membership payment " +
                        "of $%.2f is due soon.\n\nPlease make the payment at your earliest convenience " +
                        "to avoid any interruption in your services.\n\nThank you,\nGym Management Team",
                name, amount
        );
        sendEmail(email, subject, body);
    }

    @Override
    public void sendAttendanceConfirmation(String email, String name, String checkInTime) {
        String subject = "Attendance Confirmation";
        String body = String.format(
                "Dear %s,\n\nYour gym attendance has been recorded successfully.\n" +
                        "Check-in time: %s\n\nThank you for visiting our gym!\n\nBest regards,\nGym Management Team",
                name, checkInTime
        );
        sendEmail(email, subject, body);
    }
}