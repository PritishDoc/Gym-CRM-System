package com.gymcrm.backend.service;

import com.gymcrm.backend.dto.PlanDTO;

import java.util.List;

public interface PlanService {
    PlanDTO createPlan(PlanDTO planDTO);

    PlanDTO getPlanById(Long id);

    List<PlanDTO> getAllPlans();

    List<PlanDTO> getActivePlans();

    PlanDTO updatePlan(Long id, PlanDTO planDTO);

    void deletePlan(Long id);
}
