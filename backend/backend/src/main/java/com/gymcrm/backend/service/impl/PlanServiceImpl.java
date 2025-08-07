package com.gymcrm.backend.service.impl;

import com.gymcrm.backend.dto.PlanDTO;
import com.gymcrm.backend.exception.PlanNotFoundException;
import com.gymcrm.backend.model.Plan;
import com.gymcrm.backend.repository.PlanRepository;
import com.gymcrm.backend.service.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlanServiceImpl implements PlanService {

    @Autowired
    private PlanRepository planRepository;

    @Override
    public PlanDTO createPlan(PlanDTO planDTO) {
        Plan plan = new Plan();
        plan.setName(planDTO.getName());
        plan.setDescription(planDTO.getDescription());
        plan.setPrice(planDTO.getPrice());
        plan.setDurationDays(planDTO.getDurationDays());
        plan.setActive(planDTO.isActive());

        Plan savedPlan = planRepository.save(plan);
        return mapToDTO(savedPlan);
    }

    @Override
    public PlanDTO getPlanById(Long id) {
        Plan plan = planRepository.findById(id)
                .orElseThrow(() -> new PlanNotFoundException("Plan not found with id: " + id));
        return mapToDTO(plan);
    }

    @Override
    public List<PlanDTO> getAllPlans() {
        return planRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PlanDTO> getActivePlans() {
        return planRepository.findByIsActive(true)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PlanDTO updatePlan(Long id, PlanDTO planDTO) {
        Plan existingPlan = planRepository.findById(id)
                .orElseThrow(() -> new PlanNotFoundException("Plan not found with id: " + id));

        existingPlan.setName(planDTO.getName());
        existingPlan.setDescription(planDTO.getDescription());
        existingPlan.setPrice(planDTO.getPrice());
        existingPlan.setDurationDays(planDTO.getDurationDays());
        existingPlan.setActive(planDTO.isActive());

        Plan updatedPlan = planRepository.save(existingPlan);
        return mapToDTO(updatedPlan);
    }

    @Override
    public void deletePlan(Long id) {
        if (!planRepository.existsById(id)) {
            throw new PlanNotFoundException("Plan not found with id: " + id);
        }
        planRepository.deleteById(id);
    }

    private PlanDTO mapToDTO(Plan plan) {
        PlanDTO dto = new PlanDTO();
        dto.setId(plan.getId());
        dto.setName(plan.getName());
        dto.setDescription(plan.getDescription());
        dto.setPrice(plan.getPrice());
        dto.setDurationDays(plan.getDurationDays());
        dto.setActive(plan.isActive());
        return dto;
    }
}