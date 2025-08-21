package com.gymcrm.backend.service.impl;

import com.gymcrm.backend.dto.AttendanceDto;
import com.gymcrm.backend.model.Attendance;
import com.gymcrm.backend.model.User;
import com.gymcrm.backend.repository.AttendanceRepository;
import com.gymcrm.backend.repository.UserRepository;
import com.gymcrm.backend.service.AttendanceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final UserRepository userRepository;

    public AttendanceServiceImpl(AttendanceRepository attendanceRepository, UserRepository userRepository) {
        this.attendanceRepository = attendanceRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public AttendanceDto checkIn(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        LocalDate today = LocalDate.now();
        Attendance attendance = attendanceRepository.findByUserIdAndDate(userId, today);

        if (attendance == null) {
            attendance = new Attendance(user, today, true, LocalDateTime.now(), null);
        } else {
            attendance.setCheckInTime(LocalDateTime.now());
            attendance.setPresent(true);
        }

        Attendance saved = attendanceRepository.save(attendance);

        return new AttendanceDto(
                saved.getUser().getId(),
                saved.getUser().getName(),
                saved.getDate(),
                saved.isPresent(),
                saved.getCheckInTime(),
                saved.getCheckOutTime()
        );
    }

    @Override
    @Transactional
    public AttendanceDto checkOut(Long userId) {
        LocalDate today = LocalDate.now();
        Attendance attendance = attendanceRepository.findByUserIdAndDate(userId, today);

        if (attendance == null) {
            throw new RuntimeException("User has not checked in today.");
        }

        attendance.setCheckOutTime(LocalDateTime.now());
        Attendance saved = attendanceRepository.save(attendance);

        return new AttendanceDto(
                saved.getUser().getId(),
                saved.getUser().getName(),
                saved.getDate(),
                saved.isPresent(),
                saved.getCheckInTime(),
                saved.getCheckOutTime()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<AttendanceDto> getTodayAttendance() {
        LocalDate today = LocalDate.now();
        return attendanceRepository.findByDate(today)
                .stream()
                .map(a -> new AttendanceDto(
                        a.getUser().getId(),
                        a.getUser().getName(),
                        a.getDate(),
                        a.isPresent(),
                        a.getCheckInTime(),
                        a.getCheckOutTime()
                ))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Map<Long, Long> getMonthlyAttendance(int year, int month) {
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate start = yearMonth.atDay(1);
        LocalDate end = yearMonth.atEndOfMonth();

        return attendanceRepository.findByDateBetween(start, end) // ✅ only query required range
                .stream()
                .collect(Collectors.groupingBy(
                        a -> a.getUser().getId(),
                        Collectors.counting()
                ));
    }
}
