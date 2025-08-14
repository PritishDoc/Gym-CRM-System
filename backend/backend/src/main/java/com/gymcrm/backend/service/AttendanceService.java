//package com.gymcrm.backend.service;
//
//import com.gymcrm.backend.dto.AttendanceDTO;
//import com.gymcrm.backend.dto.AttendanceRequest;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//public interface AttendanceService {
//    AttendanceDTO checkInUser(AttendanceRequest request);
//    AttendanceDTO checkOutUser(Long attendanceId);
//    List<AttendanceDTO> getAllAttendanceRecords();
//    List<AttendanceDTO> getAttendanceByUser(Long userId);
//    List<AttendanceDTO> getActiveCheckIns();
//    AttendanceDTO getAttendanceById(Long attendanceId);
//    AttendanceDTO updateCheckOutNotes(Long attendanceId, String notes);
//
//    List<AttendanceDTO> getAttendanceBetweenDates(LocalDateTime start, LocalDateTime end);
//
//    Double getUserAverageSessionDuration(Long userId);
//}