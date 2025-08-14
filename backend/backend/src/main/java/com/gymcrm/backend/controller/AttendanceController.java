//package com.gymcrm.backend.controller;
//
//import com.gymcrm.backend.dto.AttendanceDTO;
//import com.gymcrm.backend.dto.AttendanceRequest;
//import com.gymcrm.backend.service.AttendanceService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/attendance")
//@RequiredArgsConstructor
//public class AttendanceController {
//
//    private final AttendanceService attendanceService;
//
//    // Check-in a user
//    @PostMapping("/check-in")
//    public ResponseEntity<AttendanceDTO> checkInUser(
//            @RequestBody AttendanceRequest request,
//            @RequestHeader("Authorization") String token) {
//        // Token verification logic would go here
//        return ResponseEntity.ok(attendanceService.checkInUser(request));
//    }
//
//    // Check-out a user
//    @PatchMapping("/{attendanceId}/check-out")
//    public ResponseEntity<AttendanceDTO> checkOutUser(
//            @PathVariable Long attendanceId,
//            @RequestHeader("Authorization") String token) {
//        // Token verification logic would go here
//        return ResponseEntity.ok(attendanceService.checkOutUser(attendanceId));
//    }
//
//    // Get all attendance records
//    @GetMapping
//    public ResponseEntity<List<AttendanceDTO>> getAllAttendanceRecords() {
//        return ResponseEntity.ok(attendanceService.getAllAttendanceRecords());
//    }
//
//    // Get attendance records for a specific user
//    @GetMapping("/user/{userId}")
//    public ResponseEntity<List<AttendanceDTO>> getAttendanceByUser(
//            @PathVariable Long userId) {
//        return ResponseEntity.ok(attendanceService.getAttendanceByUser(userId));
//    }
//
//    // Get current active check-ins (users currently in the gym)
//    @GetMapping("/active")
//    public ResponseEntity<List<AttendanceDTO>> getActiveCheckIns() {
//        return ResponseEntity.ok(attendanceService.getActiveCheckIns());
//    }
//
//    // Get attendance record by ID
//    @GetMapping("/{attendanceId}")
//    public ResponseEntity<AttendanceDTO> getAttendanceById(
//            @PathVariable Long attendanceId) {
//        return ResponseEntity.ok(attendanceService.getAttendanceById(attendanceId));
//    }
//}