//package com.gymcrm.backend.repository;
//
//import com.gymcrm.backend.model.Attendance;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Repository
//public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
//    List<Attendance> findByUserId(Long userId);
//    List<Attendance> findByCheckOutTimeIsNull();
//    boolean existsByUserIdAndCheckOutTimeIsNull(Long userId);
//
//    List<Attendance> findByCheckInTimeBetween(LocalDateTime start, LocalDateTime end);
//    List<Attendance> findByUserIdAndCheckOutTimeIsNotNull(Long userId);
//}