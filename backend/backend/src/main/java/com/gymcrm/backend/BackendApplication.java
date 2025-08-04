package com.gymcrm.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@RestController
@RequestMapping("/api")
public class BackendApplication {

	private final JavaMailSender mailSender;

	public BackendApplication(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	// Test database connection
	@GetMapping("/test/db")
	public String testDB() {
		return "Database connection working!";
	}

	// Test email service
	@GetMapping("/test/email")
	public String testEmail(@RequestParam String to) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject("Test Email from Gym CRM");
		message.setText("This is a test email from your application.");
		mailSender.send(message);
		return "Email sent to " + to;
	}

	// Basic health check
	@GetMapping("/health")
	public String healthCheck() {
		return "Application is running!";
	}
}