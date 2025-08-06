package com.gymcrm.backend.controller;

import com.gymcrm.backend.dto.UserRequestDto;
import com.gymcrm.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRequestDto dto) {
        String result = userService.registerUser(dto);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/verify")
    public String verifyOtp(@RequestParam String email, @RequestParam String otp) {
        return userService.verifyOtp(email, otp);
    }
}