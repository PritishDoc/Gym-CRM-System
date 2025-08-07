package com.gymcrm.backend.controller;

import com.gymcrm.backend.dto.PlanDTO;
import com.gymcrm.backend.dto.PlanRequest;
import com.gymcrm.backend.service.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plans")
@CrossOrigin(origins = "*")
public class PlanController {

    @Autowired
    private PlanService planService;

    @PostMapping
    public ResponseEntity<?> createPlan(@RequestBody PlanDTO planDTO) {
        try {
            PlanDTO createdPlan = planService.createPlan(planDTO);
            return ResponseEntity.ok(createdPlan);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPlanById(@PathVariable Long id) {
        try {
            PlanDTO plan = planService.getPlanById(id);
            return ResponseEntity.ok(plan);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllPlans() {
        try {
            List<PlanDTO> plans = planService.getAllPlans();
            return ResponseEntity.ok(plans);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePlan(@PathVariable Long id, @RequestBody PlanDTO planDTO) {
        try {
            PlanDTO updatedPlan = planService.updatePlan(id, planDTO);
            return ResponseEntity.ok(updatedPlan);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePlan(@PathVariable Long id) {
        try {
            planService.deletePlan(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Additional endpoints for plan-specific features
    @GetMapping("/active")
    public ResponseEntity<?> getActivePlans() {
        try {
            List<PlanDTO> activePlans = planService.getActivePlans();
            return ResponseEntity.ok(activePlans);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}