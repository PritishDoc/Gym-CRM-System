//package com.gymcrm.backend.model;
//
//import jakarta.persistence.*;
//import lombok.*;
//import java.time.LocalDateTime;
//import java.time.Duration;
//
//@Entity
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//@Table(name = "attendances")
//public class Attendance {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id", nullable = false)
//    private User user;
//
//    @Column(name = "gym_id")
//    private Long gymId;
//
//    @Column(name = "check_in_time", nullable = false)
//    private LocalDateTime checkInTime;
//
//    @Column(name = "check_out_time")
//    private LocalDateTime checkOutTime;
//
//    @Column(name = "check_in_notes")
//    private String checkInNotes;
//
//    @Column(name = "check_out_notes")
//    private String checkOutNotes;
//
//    @Column(nullable = false)
//    private String status;
//
//    @Column(name = "duration_minutes")
//    private Long durationMinutes;
//
//    // Business logic methods
//    public double calculateDurationMinutes() {
//        if (checkOutTime == null) {
//            return 0;
//        }
//        Duration duration = Duration.between(checkInTime, checkOutTime);
//        return duration.toMinutes();
//    }
//
//    public void completeSession() {
//        if (checkOutTime == null) {
//            checkOutTime = LocalDateTime.now();
//            status = "COMPLETED";
//            durationMinutes = (long) calculateDurationMinutes();
//        }
//    }
//
//    // Custom equals and hashCode implementations
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!(o instanceof Attendance)) return false;
//        Attendance that = (Attendance) o;
//        return id != null && id.equals(that.id);
//    }
//
//    @Override
//    public int hashCode() {
//        return getClass().hashCode();
//    }
//}