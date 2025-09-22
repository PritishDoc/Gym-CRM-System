package com.gymcrm.backend.repository;

import com.gymcrm.backend.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);
    Optional<Member> findByEmailIgnoreCase(String email); // Add case-insensitive search
    boolean existsByEmail(String email);
}
