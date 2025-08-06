package com.gymcrm.backend.repository;

import com.gymcrm.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByEmailIgnoreCase(String email); // Add case-insensitive search
    boolean existsByEmail(String email);
}