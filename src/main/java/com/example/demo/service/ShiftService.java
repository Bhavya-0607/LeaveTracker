package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo.Dto.AttendanceCalendarDTO;
import com.example.demo.Dto.ShiftCalendarDTO;
import com.example.demo.entity.Attendance;
import com.example.demo.entity.ShiftAssignment;
import com.example.demo.repository.AttendanceRepository;
import com.example.demo.repository.ShiftAssignmentRepository;

@Service

public class ShiftService {

    private final ShiftAssignmentRepository shiftAssignmentRepository;
    private final AttendanceRepository attendanceRepository;
    public ShiftService(ShiftAssignmentRepository shiftAssignmentRepository,
            AttendanceRepository attendanceRepository) {
this.shiftAssignmentRepository = shiftAssignmentRepository;
this.attendanceRepository = attendanceRepository;
}

    // Get Shift Calendar data for a specific employee
    public List<ShiftCalendarDTO> getShiftCalendar(Long empId, LocalDate start, LocalDate end) {
        List<ShiftAssignment> shifts = shiftAssignmentRepository.findByEmployee_IdAndDateBetween(empId, start, end);
        return shifts.stream().map(shift -> {
            ShiftCalendarDTO dto = new ShiftCalendarDTO();
            dto.setDate(shift.getDate());
            dto.setShiftName(shift.getShift().getName());
            dto.setShiftTiming(
                shift.getShift().getStartTime() + " - " + shift.getShift().getEndTime()
            );
            return dto;
        }).collect(Collectors.toList());
    }

    // Get Attendance Calendar data for a specific employee
    public List<AttendanceCalendarDTO> getAttendanceCalendar(Long empId, LocalDate start, LocalDate end) {
        List<Attendance> list = attendanceRepository.findByEmployeeIdAndDateBetween(empId, start, end);
        return list.stream().map(att -> {
            AttendanceCalendarDTO dto = new AttendanceCalendarDTO();
            dto.setDate(att.getDate());
            dto.setPresent(att.isPresent());
            dto.setInTime(att.getInTime());
            dto.setOutTime(att.getOutTime());
            dto.setShiftName(att.getDayType() != null ? att.getDayType().toString() : ""); // null safety
            return dto;
        }).collect(Collectors.toList());
    }

    // Placeholder method for future shift calendar logic
    public List<ShiftCalendarDTO> getShiftCalendarData(LocalDate fromDate, LocalDate toDate) {
        // Implement logic here as per your use case
        return List.of(); // Return empty list as default
    }
}
