package com.gymcrm.backend.service.impl;

import com.gymcrm.backend.dto.MemberRespose;
import com.gymcrm.backend.dto.MemberRequestDto;
import com.gymcrm.backend.dto.UserResponse;
import com.gymcrm.backend.model.Member;
import com.gymcrm.backend.model.enums.Role;
import com.gymcrm.backend.repository.MemberRepository;
import com.gymcrm.backend.service.MemberService;
import com.gymcrm.backend.util.EmailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


@Service
public class MemberServiceimpl implements MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private EmailSender emailSender;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public String registerMember(MemberRequestDto dto)
    {
        // Validate input
        if (dto.getEmail() == null || dto.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }

        if(memberRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        // Generate and set OTP
        String otp = generateOtp();



            // Create and save user
            Member member = memberRepository.save(
                    Member.builder()
                    .name(dto.getName())
                    .email(dto.getEmail().toLowerCase()) // Normalize email case
                    .contactNo(dto.getContactNo())
                    .gender(dto.getGender())
                    .membership(dto.getMembership())
                    .preferredTime(dto.getPreferredTime())
                            .age(dto.getAge())
                            .trainer(dto.getTrainer())
                    .otp(otp)
                    .role(Role.MEMBER) // Set default role
                            .Date_of_Joining(dto.getDate_of_Joining().toString())
//                            .created()
                    .build()
            );

        member.setOtp(otp);
        member.setVerified(false); // reset verified status

        try {
            // Send OTP email after successful save
            emailSender.sendEmail(
                    member.getEmail(),
                    "Your OTP for Gym CRM",
                    "Your verification code is: " + otp + "\n\n" +
                            "This code will expire in 10 minutes."
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to send OTP email. Please try again.");
        }

        return "Registration successful. OTP sent to " + member.getEmail();
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
        Member member = memberRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        // Check OTP status
        if (member.getOtp() == null) {
            throw new RuntimeException("No active OTP found. Please request a new OTP.");
        }

        // Verify OTP
        if (!member.getOtp().equals(otp)) {
            throw new RuntimeException("Invalid OTP. Please try again.");
        }

        // Update user status
        member.setVerified(true);
        member.setOtp(null); // Clear OTP after verification
        memberRepository.save(member);

        return "OTP verified successfully. Your account is now active!";
    }

    @Override
    public MemberRespose getMemberByEmail(String email) {
        Member member = memberRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
        return convertToUserResponse(member);
    }


    @Override
    @Transactional
    public MemberRespose updateMeber(Long id, MemberRequestDto dto) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        // Update fields if provided
        if (dto.getName() != null) member.setName(dto.getName());
        if (dto.getContactNo() != null) member.setContactNo(dto.getContactNo());
        if (dto.getGender() != null) member.setGender(dto.getGender());
        if (dto.getMembership() != null) member.setMembership(dto.getMembership());
        if (dto.getPreferredTime() != null) member.setPreferredTime(dto.getPreferredTime());
        if (dto.getAge() != null) member.setAge(dto.getAge());
        if (dto.getTrainer() != null) member.setTrainer(dto.getTrainer());

        if (dto.getEmail() != null) {
            if (!dto.getEmail().equalsIgnoreCase(member.getEmail()) &&
                    memberRepository.findByEmailIgnoreCase(dto.getEmail()).isPresent()) {
                throw new RuntimeException("Email already in use");
            }
            member.setEmail(dto.getEmail().toLowerCase());
        }


        Member updatedmember = memberRepository.save(member);
        return convertToUserResponse(updatedmember);
    }

    @Override
    @Transactional
    public void deleteMember(Long id) {
        if (!memberRepository.existsById(id)) {
            throw new RuntimeException("User not found with id: " + id);
        }
        memberRepository.deleteById(id);
    }

    @Override
    public List<MemberRespose> getAllMembers() {
        return memberRepository.findAll().stream()
                .map(this::convertToUserResponse)
                .collect(Collectors.toList());
    }

    @Override
    public MemberRespose getMemberById(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Member not found with id: " + id));
        return convertToUserResponse(member);
    }

    private String generateOtp() {
        return String.format("%04d", new Random().nextInt(10000));
    }

    private MemberRespose convertToUserResponse(Member member) {
        return MemberRespose.builder()
                .id(member.getId())
                .name(member.getName())
                .email(member.getEmail())
                .contactNo(member.getContactNo())
                .gender(member.getGender())
                .membership(member.getMembership())
                .preferredTime(member.getPreferredTime())
                .verified(member.isVerified())
                .Date_of_Joining(member.getDate_of_Joining())
//                .createdAt(member.getCreatedAt())
                .build();
    }
}


