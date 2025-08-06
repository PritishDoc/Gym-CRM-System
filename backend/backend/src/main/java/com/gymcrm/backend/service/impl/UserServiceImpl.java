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
        String otp = String.format("%04d", new Random().nextInt(10000));

        User user = User.builder()
                .fullname(dto.getFullname())
                .email(dto.getEmail())
                .contactNo(dto.getContactNo())
                .gender(dto.getGender())
                .membership(dto.getMembership())
                .preferredTime(dto.getPreferredTime())
                .otp(otp)
                .verified(false)
                .createdAt(LocalDateTime.now().toString())
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
}