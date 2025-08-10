package com.gymcrm.backend.service.impl;

import com.gymcrm.backend.dto.*;
import com.gymcrm.backend.model.User;
import com.gymcrm.backend.model.enums.Role;
import com.gymcrm.backend.repository.UserRepository;
import com.gymcrm.backend.service.UserService;
import com.gymcrm.backend.util.EmailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailSender emailSender;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public String registerUser(UserRequestDto dto) {
        // Validate input
        if (dto.getEmail() == null || dto.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        if (dto.getPassword() == null || dto.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }

        // Check if email already exists
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        // Generate and set OTP
        String otp = generateOtp();

        // Create and save user
        User user = User.builder()
                .fullname(dto.getFullname())
                .email(dto.getEmail().toLowerCase()) // Normalize email case
                .contactNo(dto.getContactNo())
                .gender(dto.getGender())
                .membership(dto.getMembership())
                .preferredTime(dto.getPreferredTime())
                .password(passwordEncoder.encode(dto.getPassword()))
                .otp(otp)
                .verified(false)
                .active(true)
                .role(Role.USER) // Set default role
                .createdAt(LocalDateTime.now().toString())
                .build();

        user = userRepository.save(user); // Ensure save is successful

        try {
            // Send OTP email after successful save
            emailSender.sendEmail(
                    user.getEmail(),
                    "Your OTP for Gym CRM",
                    "Your verification code is: " + otp + "\n\n" +
                            "This code will expire in 10 minutes."
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to send OTP email. Please try again.");
        }

        return "Registration successful. OTP sent to " + user.getEmail();
    }

    @Override
    @Transactional
    public String verifyOtp(String email, String otp) {
        // Validate input
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        if (otp == null || otp.trim().isEmpty()) {
            throw new IllegalArgumentException("OTP cannot be empty");
        }

        // Find user (case-insensitive)
        User user = userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        // Check OTP status
        if (user.getOtp() == null) {
            throw new RuntimeException("No active OTP found. Please request a new OTP.");
        }

        // Verify OTP
        if (!user.getOtp().equals(otp)) {
            throw new RuntimeException("Invalid OTP. Please try again.");
        }

        // Update user status
        user.setVerified(true);
        user.setOtp(null); // Clear OTP after verification
        userRepository.save(user);

        return "OTP verified successfully. Your account is now active!";
    }

    @Override
    public UserResponse getUserByEmail(String email) {
        User user = userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
        return convertToUserResponse(user);
    }

    @Override
    @Transactional
    public UserResponse updateUser(Long id, UserRequestDto dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        // Update fields if provided
        if (dto.getFullname() != null) user.setFullname(dto.getFullname());
        if (dto.getContactNo() != null) user.setContactNo(dto.getContactNo());
        if (dto.getGender() != null) user.setGender(dto.getGender());
        if (dto.getMembership() != null) user.setMembership(dto.getMembership());
        if (dto.getPreferredTime() != null) user.setPreferredTime(dto.getPreferredTime());
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        User updatedUser = userRepository.save(user);
        return convertToUserResponse(updatedUser);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToUserResponse)
                .collect(Collectors.toList());
    }

    private String generateOtp() {
        return String.format("%04d", new Random().nextInt(10000));
    }

    private UserResponse convertToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .fullname(user.getFullname())
                .email(user.getEmail())
                .contactNo(user.getContactNo())
                .gender(user.getGender())
                .membership(user.getMembership())
                .preferredTime(user.getPreferredTime())
                .verified(user.isVerified())
                .createdAt(user.getCreatedAt())
                .build();
    }
}