package com.gymcrm.backend.service;

public interface UserService {


import com.gymcrm.backend.dto.UserRequestDto;
import com.gymcrm.backend.model.User;
import com.gymcrm.backend.repository.UserRepository;
import com.gymcrm.backend.service.UserService;
import com.gymcrm.backend.util.EmailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

    @Service
    public class UserServiceImpl implements UserService
    {

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private EmailSender emailSender;

        @Override
        public String registerUser(RegisterRequest dto) {
            {
                Optional<User> existing = userRepository.findByEmail(dto.getEmail());
                if (existing.isPresent()) return "User already exists";

                User user = new User();
                user.setName(dto.getName());
                user.setEmail(dto.getEmail());
                user.setPassword(dto.getPassword());

                String otp = String.valueOf(new Random().nextInt(999999));
                user.setOtp(otp);
                user.setVerified(false);

                userRepository.save(user);
                emailSender.sendOtp(dto.getEmail(), otp);

                return "OTP sent to email.";
            }

            @Override
            public String verifyOtp(String email, String otp) {
                Optional<User> userOpt = userRepository.findByEmail(email);
                if (userOpt.isEmpty()) return "User not found";

                User user = userOpt.get();
                if (user.getOtp().equals(otp)) {
                    user.setVerified(true);
                    userRepository.save(user);
                    return "User verified!";
                }
                return "Invalid OTP";
            }
        }