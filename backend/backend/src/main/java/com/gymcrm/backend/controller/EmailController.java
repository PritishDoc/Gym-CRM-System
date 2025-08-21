package com.gymcrm.backend.controller;

import com.gymcrm.backend.dto.*;
import com.gymcrm.backend.service.EmailService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendEmail(@Valid @RequestBody EmailRequest emailRequest) {
        emailService.sendEmail(emailRequest.getTo(), emailRequest.getSubject(), emailRequest.getBody());
        return ResponseEntity.ok("Email sent successfully");
    }

    @PostMapping("/send-welcome")
    public ResponseEntity<String> sendWelcomeEmail(@Valid @RequestBody WelcomeEmailRequest welcomeRequest) {
        emailService.sendWelcomeEmail(welcomeRequest.getEmail(), welcomeRequest.getName());
        return ResponseEntity.ok("Welcome email sent successfully");
    }

    @PostMapping("/send-payment-reminder")
    public ResponseEntity<String> sendPaymentReminder(@Valid @RequestBody PaymentReminderRequest paymentRequest) {
        emailService.sendPaymentReminder(paymentRequest.getEmail(), paymentRequest.getName(), paymentRequest.getAmount());
        return ResponseEntity.ok("Payment reminder sent successfully");
    }

    @PostMapping("/send-attendance-confirmation")
    public ResponseEntity<String> sendAttendanceConfirmation(@Valid @RequestBody AttendanceConfirmationRequest attendanceRequest) {
        emailService.sendAttendanceConfirmation(attendanceRequest.getEmail(), attendanceRequest.getName(), attendanceRequest.getCheckInTime());
        return ResponseEntity.ok("Attendance confirmation sent successfully");
    }
}