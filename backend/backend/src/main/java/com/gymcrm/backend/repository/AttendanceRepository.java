package com.gymcrm.backend.repository;

import com.gymcrm.backend.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    List<Attendance> findByDate(LocalDate date);

    List<Attendance> findByUserIdAndDateBetween(Long userId, LocalDate startDate, LocalDate endDate);

    Attendance findByUserIdAndDate(Long userId, LocalDate date);

    List<Attendance> findByDateBetween(LocalDate start, LocalDate end);

}
