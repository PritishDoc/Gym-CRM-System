package com.gymcrm.backend.service.impl;

import com.gymcrm.backend.dto.UserRequestDto;
import com.gymcrm.backend.model.User;
import com.gymcrm.backend.repository.UserRepository;
import com.gymcrm.backend.service.UserService;
import com.gymcrm.backend.util.EmailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailSender emailSender;

    @Override
    public String registerUser(UserRequestDto dto) {
        // Generate 4-digit OTP with leading zeros if needed
        String otp = String.format("%04d", new Random().nextInt(10000));

        // Build user object
        User user = User.builder()
                .fullName(dto.getFullname())
                .email(dto.getEmail())
                .contactNo(dto.getContactNo())
                .gender(dto.getGender())
                .membership(dto.getMembership())
                .preferredTime(dto.getPreferredTime())
                .otp(otp)
                .verified(false)
                .createdAt(LocalDateTime.now().toString())
                .build();

        // Save to DB
        userRepository.save(user);

        // Send OTP to user email
        emailSender.sendEmail(dto.getEmail(), "Your OTP for Gym CRM", "Your OTP is: " + otp);

        return "OTP sent successfully to " + dto.getEmail();
    }

    @Override
    public String registerUsers(UserRequestDto userRequestDto) {
        // Optional: Remove this if you don’t need both registerUser(s)
        return registerUser(userRequestDto);
    }

    @Override
    public String verifyOtp(String email, String otp) {
        // Implement OTP verification logic later
        return "";
    }
}
