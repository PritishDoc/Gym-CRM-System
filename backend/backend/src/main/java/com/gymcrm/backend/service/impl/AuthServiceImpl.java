package com.gymcrm.backend.service.impl;

import com.gymcrm.backend.dto.AuthResponse;
import com.gymcrm.backend.dto.LoginRequest;
import com.gymcrm.backend.dto.RegisterRequest;
import com.gymcrm.backend.exception.AuthenticationException;
import com.gymcrm.backend.exception.UserAlreadyExistsException;
import com.gymcrm.backend.model.TokenBlacklist;
import com.gymcrm.backend.model.User;
import com.gymcrm.backend.model.enums.Role;
import com.gymcrm.backend.repository.TokenBlacklistRepository;
import com.gymcrm.backend.repository.UserRepository;
import com.gymcrm.backend.service.AuthService;
import com.gymcrm.backend.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenBlacklistRepository tokenBlacklistRepository;

    @Override
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        log.info("Attempting to register new user with email: {}", request.getEmail());

        // Check if user already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            log.error("Registration failed - user already exists with email: {}", request.getEmail());
            throw new UserAlreadyExistsException("Email already in use");
        }
        // Generate and set OTP
        String otp = generateOtp();

        // Create new user
        var user = User.builder()
                .fullname(request.getFullname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole() != null ? request.getRole() : Role.USER)
                .verified(false)
                .active(true)
                .gender(request.getGender())
                .membership(request.getMembership())
                .otp(otp)
                .contactNo(request.getContactNo())
                .createdAt(LocalDateTime.now().toString())
                .build();

        userRepository.save(user);
        log.info("New user registered successfully with email: {}", request.getEmail());

        // Generate JWT token
        var jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder()
                .token(jwtToken)
                .fullname(user.getFullname())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    @Override
    public AuthResponse authenticate(LoginRequest request) {
        log.info("Authentication attempt for email: {}", request.getEmail());

        try {
            // Authenticate user
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

            // Get user details
            var user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> {
                        log.error("User not found after authentication: {}", request.getEmail());
                        return new UsernameNotFoundException("User not found");
                    });

            // Check if account is active
            if (!user.isActive()) {
                log.error("Login attempt for inactive account: {}", request.getEmail());
                throw new DisabledException("Account is disabled");
            }

            // Generate JWT token
            var jwtToken = jwtService.generateToken(user);
            log.info("User authenticated successfully: {}", request.getEmail());

            return AuthResponse.builder()
                    .token(jwtToken)
                    .email(user.getEmail())
                    .role(user.getRole())
                    .fullname(user.getFullname())
                    .build();

        } catch (BadCredentialsException e) {
            log.error("Invalid credentials for email: {}", request.getEmail());
            throw new AuthenticationException("Invalid email or password");
        } catch (Exception e) {
            log.error("Authentication error for email {}: {}", request.getEmail(), e.getMessage());
            throw new AuthenticationException("Authentication failed");
        }
    }

    @Override
    @Transactional
    public void logout(String token) {
        log.info("Logging out token: {}", token.substring(0, 10) + "...");

        // Check if token is already blacklisted
        if (tokenBlacklistRepository.existsByToken(token)) {
            log.warn("Token already blacklisted: {}", token.substring(0, 10) + "...");
            return;
        }

        // Add token to blacklist
        TokenBlacklist blacklistedToken = TokenBlacklist.builder()
                .token(token)
                .blacklistedAt(new Date())
                .build();

        tokenBlacklistRepository.save(blacklistedToken);
        log.info("Token successfully blacklisted");
    }

    private String generateOtp() {
        return String.format("%04d", new Random().nextInt(10000));
    }
}