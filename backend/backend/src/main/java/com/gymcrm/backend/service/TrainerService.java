package com.gymcrm.backend.service;

import com.gymcrm.backend.dto.TrainerRequestDto;
import com.gymcrm.backend.dto.TrainerRespose;

import java.util.List;

public interface TrainerService {

    String registerTrainer(TrainerRequestDto dto);

    String verifyOtp(String email, String otp);

    TrainerRespose getTrainerByEmail(String email);

    TrainerRespose updateTrainer(Long id, TrainerRequestDto dto);

    void deleteTrainer(Long id);

    List<TrainerRespose> getAllTrainers();

    TrainerRespose getTrainerById(Long id);
}
