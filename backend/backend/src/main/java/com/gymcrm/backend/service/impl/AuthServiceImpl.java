package com.gymcrm.backend.service.impl;

import com.gymcrm.backend.dto.AuthResponse;
import com.gymcrm.backend.dto.LoginRequest;
import com.gymcrm.backend.dto.RegisterRequest;
import com.gymcrm.backend.model.TokenBlacklist;
import com.gymcrm.backend.model.User;
import com.gymcrm.backend.model.enums.Role;
import com.gymcrm.backend.repository.TokenBlacklistRepository;
import com.gymcrm.backend.repository.UserRepository;
import com.gymcrm.backend.service.AuthService;
import com.gymcrm.backend.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenBlacklistRepository tokenBlacklistRepository;

    @Override
    public AuthResponse register(RegisterRequest request) {
        var user = User.builder()
                .fullname(request.getFullname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .verified(false)
                .active(true)
                .createdAt(LocalDateTime.now().toString())
                .build();

        userRepository.save(user);

        var jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }

    @Override
    public AuthResponse authenticate(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();

        var jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }
    @Override
    public void logout(String token) {
        // Add token to blacklist
        TokenBlacklist blacklistedToken = TokenBlacklist.builder()
                .token(token)
                .blacklistedAt(new Date())
                .build();
        tokenBlacklistRepository.save(blacklistedToken);
    }
}