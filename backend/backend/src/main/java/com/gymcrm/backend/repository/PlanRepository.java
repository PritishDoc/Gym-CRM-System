package com.gymcrm.backend.repository;

import com.gymcrm.backend.model.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PlanRepository extends JpaRepository<Plan, Long> {
    List<Plan> findByIsActive(boolean isActive);
}