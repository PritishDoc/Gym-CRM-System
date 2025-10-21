package com.gymcrm.backend.service.impl;

import com.gymcrm.backend.dto.TrainerRespose;
import com.gymcrm.backend.dto.TrainerRequestDto;
import com.gymcrm.backend.model.Trainer;
import com.gymcrm.backend.model.enums.Role;
import com.gymcrm.backend.repository.TrainerRepository;
import com.gymcrm.backend.service.TrainerService;
import com.gymcrm.backend.util.EmailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class TrainerServiceImpl implements TrainerService {

    @Autowired
    private TrainerRepository trainerRepository;

    @Autowired
    private EmailSender emailSender;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String registerTrainer(TrainerRequestDto dto) {
        // Validate input
        if (dto.getEmail() == null || dto.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }

        if (trainerRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        // Generate OTP
        String otp = generateOtp();

        Trainer trainer = trainerRepository.save(
                Trainer.builder()
                        .name(dto.getName())
                        .email(dto.getEmail().toLowerCase())
                        .contactNo(dto.getContactNo())
                        .gender(dto.getGender())
                        .age(dto.getAge())
                        .address(dto.getAddress())
                        .experienceYears(dto.getExperienceYears())
                        .preferredTime(dto.getPreferredTime())
                        .otp(otp)
                        .verified(false)
                        .role(Role.TRAINER)
                        .dateOfJoining(dto.getDateOfJoining())
                        .build()
        );

        trainer.setOtp(otp);
        trainer.setVerified(false);

        try {
            emailSender.sendEmail(
                    trainer.getEmail(),
                    "Your OTP for Gym CRM - Trainer Registration",
                    "Your verification code is: " + otp + "\n\n" +
                            "This code will expire in 10 minutes."
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to send OTP email. Please try again.");
        }

        return "Registration successful. OTP sent to " + trainer.getEmail();
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

        Trainer trainer = trainerRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new RuntimeException("Trainer not found with email: " + email));

        if (trainer.getOtp() == null) {
            throw new RuntimeException("No active OTP found. Please request a new OTP.");
        }

        if (!trainer.getOtp().equals(otp)) {
            throw new RuntimeException("Invalid OTP. Please try again.");
        }

        trainer.setVerified(true);
        trainer.setOtp(null);
        trainerRepository.save(trainer);

        return "OTP verified successfully. Your account is now active!";
    }

    @Override
    public TrainerRespose getTrainerByEmail(String email) {
        Trainer trainer = trainerRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new RuntimeException("Trainer not found with email: " + email));
        return convertToTrainerResponse(trainer);
    }

    @Override
    @Transactional
    public TrainerRespose updateTrainer(Long id, TrainerRequestDto dto) {
        Trainer trainer = trainerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Trainer not found with id: " + id));

        if (dto.getName() != null) trainer.setName(dto.getName());
        if (dto.getContactNo() != null) trainer.setContactNo(dto.getContactNo());
        if (dto.getGender() != null) trainer.setGender(dto.getGender());
        if (dto.getAge() != null) trainer.setAge(dto.getAge());
        if (dto.getAddress() != null) trainer.setAddress(dto.getAddress());
        if (dto.getExperienceYears() != null) trainer.setExperienceYears(dto.getExperienceYears());
        if (dto.getPreferredTime() != null) trainer.setPreferredTime(dto.getPreferredTime());
        if (dto.getDateOfJoining() != null) trainer.setDateOfJoining(dto.getDateOfJoining());

        if (dto.getEmail() != null) {
            if (!dto.getEmail().equalsIgnoreCase(trainer.getEmail()) &&
                    trainerRepository.findByEmailIgnoreCase(dto.getEmail()).isPresent()) {
                throw new RuntimeException("Email already in use");
            }
            trainer.setEmail(dto.getEmail().toLowerCase());
        }

        Trainer updatedTrainer = trainerRepository.save(trainer);
        return convertToTrainerResponse(updatedTrainer);
    }

    @Override
    @Transactional
    public void deleteTrainer(Long id) {
        if (!trainerRepository.existsById(id)) {
            throw new RuntimeException("Trainer not found with id: " + id);
        }
        trainerRepository.deleteById(id);
    }

    @Override
    public List<TrainerRespose> getAllTrainers() {
        return trainerRepository.findAll().stream()
                .map(this::convertToTrainerResponse)
                .collect(Collectors.toList());
    }

    @Override
    public TrainerRespose getTrainerById(Long id) {
        Trainer trainer = trainerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Trainer not found with id: " + id));
        return convertToTrainerResponse(trainer);
    }

    private String generateOtp() {
        return String.format("%04d", new Random().nextInt(10000));
    }

    private TrainerRespose convertToTrainerResponse(Trainer trainer) {
        return TrainerRespose.builder()
                .id(trainer.getId())
                .name(trainer.getName())
                .email(trainer.getEmail())
                .contactNo(trainer.getContactNo())
                .gender(trainer.getGender())
                .age(trainer.getAge())
                .address(trainer.getAddress())
                .experienceYears(trainer.getExperienceYears())
                .preferredTime(trainer.getPreferredTime())
                .verified(trainer.isVerified())
                .dateOfJoining(trainer.getDateOfJoining())
                .build();
    }
}
