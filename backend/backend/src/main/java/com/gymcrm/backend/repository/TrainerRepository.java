package com.gymcrm.backend.repository;

import com.gymcrm.backend.model.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TrainerRepository extends JpaRepository<Trainer, Long> {

    Optional<Trainer> findByEmail(String email);
    Optional<Trainer> findByEmailIgnoreCase(String email); // Add case-insensitive search
    boolean existsByEmail(String email);
}
