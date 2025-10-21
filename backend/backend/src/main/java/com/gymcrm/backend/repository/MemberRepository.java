package com.gymcrm.backend.repository;

import com.gymcrm.backend.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);
    Optional<Member> findByEmailIgnoreCase(String email);
    boolean existsByEmail(String email);

    // load trainer together with member (for single member by email)
    @Query("select m from Member m left join fetch m.trainer where lower(m.email) = lower(:email)")
    Optional<Member> findByEmailWithTrainer(@Param("email") String email);

    // load member + trainer by id
    @Query("select m from Member m left join fetch m.trainer where m.id = :id")
    Optional<Member> findByIdWithTrainer(@Param("id") Long id);

    // load all members with their trainers (use distinct to avoid duplicates)
    @Query("select distinct m from Member m left join fetch m.trainer")
    List<Member> findAllWithTrainer();
}

