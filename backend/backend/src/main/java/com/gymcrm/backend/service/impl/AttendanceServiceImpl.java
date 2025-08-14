//package com.gymcrm.backend.service.impl;
//
//import com.gymcrm.backend.dto.AttendanceDTO;
//import com.gymcrm.backend.dto.AttendanceRequest;
//import com.gymcrm.backend.exception.ResourceNotFoundException;
//import com.gymcrm.backend.model.Attendance;
//import com.gymcrm.backend.model.User;
//import com.gymcrm.backend.repository.AttendanceRepository;
//import com.gymcrm.backend.repository.UserRepository;
//import com.gymcrm.backend.service.AttendanceService;
//import lombok.RequiredArgsConstructor;
//import org.modelmapper.ModelMapper;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.Duration;
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//@Transactional
//public class AttendanceServiceImpl implements AttendanceService {
//
//    private final AttendanceRepository attendanceRepository;
//    private final UserRepository userRepository;
//    private final ModelMapper modelMapper;
//
//    @Override
//    public AttendanceDTO checkInUser(AttendanceRequest request) {
//        User user = userRepository.findById(request.getUserId())
//                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + request.getUserId()));
//
//        if (attendanceRepository.existsByUserIdAndCheckOutTimeIsNull(user.getId())) {
//            throw new IllegalStateException("User already has an active check-in session");
//        }
//
//        Attendance attendance = Attendance.builder()
//                .user(user)
//                .checkInTime(LocalDateTime.now())
//                .status("ACTIVE")
//                .checkInNotes(request.getCheckInNotes())
//                .gymId(request.getGymId())
//                .build();
//
//        Attendance savedAttendance = attendanceRepository.save(attendance);
//        return modelMapper.map(savedAttendance, AttendanceDTO.class);
//    }
//
//    @Override
//    public AttendanceDTO checkOutUser(Long attendanceId) {
//        Attendance attendance = attendanceRepository.findById(attendanceId)
//                .orElseThrow(() -> new ResourceNotFoundException("Attendance not found with id: " + attendanceId));
//
//        if (attendance.getCheckOutTime() != null) {
//            throw new IllegalStateException("User already checked out");
//        }
//
//        attendance.setCheckOutTime(LocalDateTime.now());
//        attendance.setStatus("COMPLETED");
//
//        // Calculate session duration
//        Duration duration = Duration.between(attendance.getCheckInTime(), attendance.getCheckOutTime());
//        attendance.setDurationMinutes(duration.toMinutes());
//
//        Attendance updatedAttendance = attendanceRepository.save(attendance);
//        return modelMapper.map(updatedAttendance, AttendanceDTO.class);
//    }
//
//    @Override
//    public List<AttendanceDTO> getAllAttendanceRecords() {
//        return attendanceRepository.findAll().stream()
//                .map(attendance -> modelMapper.map(attendance, AttendanceDTO.class))
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public List<AttendanceDTO> getAttendanceByUser(Long userId) {
//        if (!userRepository.existsById(userId)) {
//            throw new ResourceNotFoundException("User not found with id: " + userId);
//        }
//
//        return attendanceRepository.findByUserId(userId).stream()
//                .map(attendance -> modelMapper.map(attendance, AttendanceDTO.class))
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public List<AttendanceDTO> getActiveCheckIns() {
//        return attendanceRepository.findByCheckOutTimeIsNull().stream()
//                .map(attendance -> modelMapper.map(attendance, AttendanceDTO.class))
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public AttendanceDTO getAttendanceById(Long attendanceId) {
//        Attendance attendance = attendanceRepository.findById(attendanceId)
//                .orElseThrow(() -> new ResourceNotFoundException("Attendance not found with id: " + attendanceId));
//        return modelMapper.map(attendance, AttendanceDTO.class);
//    }
//
//    @Override
//    public AttendanceDTO updateCheckOutNotes(Long attendanceId, String notes) {
//        Attendance attendance = attendanceRepository.findById(attendanceId)
//                .orElseThrow(() -> new ResourceNotFoundException("Attendance not found with id: " + attendanceId));
//
//        attendance.setCheckOutNotes(notes);
//        Attendance updatedAttendance = attendanceRepository.save(attendance);
//        return modelMapper.map(updatedAttendance, AttendanceDTO.class);
//    }
//
//    @Override
//    public List<AttendanceDTO> getAttendanceBetweenDates(LocalDateTime start, LocalDateTime end) {
//        return attendanceRepository.findByCheckInTimeBetween(start, end).stream()
//                .map(attendance -> modelMapper.map(attendance, AttendanceDTO.class))
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public Double getUserAverageSessionDuration(Long userId) {
//        List<Attendance> completedSessions = attendanceRepository
//                .findByUserIdAndCheckOutTimeIsNotNull(userId);
//
//        if (completedSessions.isEmpty()) {
//            return 0.0;
//        }
//
//        double totalMinutes = completedSessions.stream()
//                .mapToDouble(Attendance::getDurationMinutes)
//                .sum();
//
//        return totalMinutes / completedSessions.size();
//    }
//}