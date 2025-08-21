package com.gymcrm.backend.service;

public interface EmailService {
    void sendEmail(String to, String subject, String body);
    void sendWelcomeEmail(String email, String name);
    void sendPaymentReminder(String email, String name, Double amount);
    void sendAttendanceConfirmation(String email, String name, String checkInTime);
}