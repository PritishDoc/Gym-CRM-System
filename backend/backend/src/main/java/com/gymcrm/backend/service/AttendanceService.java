package com.gymcrm.backend.service;

import com.gymcrm.backend.dto.AttendanceDto;
import java.util.List;
import java.util.Map;

public interface AttendanceService {

    AttendanceDto checkIn(Long userId);

    AttendanceDto checkOut(Long userId);

    List<AttendanceDto> getTodayAttendance();

    Map<Long, Long> getMonthlyAttendance(int year, int month);
}
