package com.gymcrm.backend.controller;

import com.gymcrm.backend.dto.MemberRequestDto;
import com.gymcrm.backend.dto.MemberRespose;
import com.gymcrm.backend.dto.VerifyRequest;
import com.gymcrm.backend.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
public class memberController {

    @Autowired
    private MemberService memberService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody MemberRequestDto dto) {
        try {
            String result = memberService.registerMember(dto);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyOtp(@RequestBody VerifyRequest request) {
        try {
            String result = memberService.verifyOtp(request.getEmail(), request.getOtp());
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/by-email")
    public ResponseEntity<?> getUserByEmail(@RequestParam String email) {
        try {
            MemberRespose user = memberService.getMemberByEmail(email);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // fetch a member by id
    @GetMapping("/{id}")
    public ResponseEntity<?> getMemberById(@PathVariable Long id) {
        try {
            MemberRespose member = memberService.getMemberById(id);
            return ResponseEntity.ok(member);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(
            @PathVariable Long id,
            @RequestBody MemberRequestDto dto
    ) {
        try {
            MemberRespose updatedUser = memberService.updateMeber(id, dto);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            memberService.deleteMember(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers() {
        try {
            List<MemberRespose> users = memberService.getAllMembers();
            return ResponseEntity.ok(users);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
