package com.gymcrm.backend.service.impl;

import com.gymcrm.backend.dto.*;
import com.gymcrm.backend.model.User;
import com.gymcrm.backend.repository.UserRepository;
import com.gymcrm.backend.service.UserService;
import com.gymcrm.backend.util.EmailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
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
    public String registerUser(UserRequestDto dto) {
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        String otp = String.format("%04d", new Random().nextInt(10000));

        User user = User.builder()
                .fullname(dto.getFullname())
                .email(dto.getEmail())
                .contactNo(dto.getContactNo())
                .gender(dto.getGender())
                .membership(dto.getMembership())
                .preferredTime(dto.getPreferredTime())
                .password(passwordEncoder.encode(dto.getPassword())) // Add password if needed
                .otp(otp)
                .verified(false)
                .createdAt(LocalDateTime.now().toString())
                .active(true)
                .build();

        userRepository.save(user);
        emailSender.sendEmail(dto.getEmail(), "Your OTP for Gym CRM", "Your OTP is: " + otp);

        return "OTP sent successfully to " + dto.getEmail();
    }

    @Override
    public String verifyOtp(String email, String otp) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getOtp().equals(otp)) {
            user.setVerified(true);
            userRepository.save(user);
            return "OTP verified successfully";
        }
        return "Invalid OTP";
    }

    @Override
    public UserResponse getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return convertToUserResponse(user);
    }

    @Override
    public UserResponse updateUser(Long id, UserRequestDto dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setFullname(dto.getFullname());
        user.setContactNo(dto.getContactNo());
        user.setGender(dto.getGender());
        user.setMembership(dto.getMembership());
        user.setPreferredTime(dto.getPreferredTime());

        User updatedUser = userRepository.save(user);
        return convertToUserResponse(updatedUser);
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.delete(user);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToUserResponse)
                .collect(Collectors.toList());
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