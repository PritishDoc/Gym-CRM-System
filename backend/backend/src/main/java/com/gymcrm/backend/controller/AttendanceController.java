package com.gymcrm.backend.controller;

import com.gymcrm.backend.dto.AttendanceDto;
import com.gymcrm.backend.service.AttendanceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    // ✅ Check-in
    @PostMapping("/checkin/{userId}")
    public AttendanceDto checkIn(@PathVariable Long userId) {
        return attendanceService.checkIn(userId);
    }

    // ✅ Check-out
    @PostMapping("/checkout/{userId}")
    public AttendanceDto checkOut(@PathVariable Long userId) {
        return attendanceService.checkOut(userId);
    }

    // ✅ Today’s attendance
    @GetMapping("/today")
    public List<AttendanceDto> getTodayAttendance() {
        return attendanceService.getTodayAttendance();
    }

    // ✅ Monthly summary
    @GetMapping("/monthly")
    public Map<Long, Long> getMonthlyAttendance(@RequestParam int year, @RequestParam int month) {
        return attendanceService.getMonthlyAttendance(year, month);
    }
}
