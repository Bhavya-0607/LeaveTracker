package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo.Dto.AttendanceCalendarDTO;
import com.example.demo.Dto.ShiftCalendarDTO;
import com.example.demo.entity.Attendance;
import com.example.demo.entity.Employee;
import com.example.demo.entity.Shift;
import com.example.demo.entity.ShiftAssignment;
import com.example.demo.repository.AttendanceRepository;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.ShiftAssignmentRepository;
import com.example.demo.repository.ShiftRepository;

@Service
public class ShiftService {

    private final ShiftAssignmentRepository shiftAssignmentRepository;
    private final AttendanceRepository attendanceRepository;
    private final ShiftRepository shiftRepository;
    private final EmployeeRepository employeeRepository;

    public ShiftService(
            ShiftAssignmentRepository shiftAssignmentRepository,
            AttendanceRepository attendanceRepository,
            ShiftRepository shiftRepository,
            EmployeeRepository employeeRepository
    ) {
        this.shiftAssignmentRepository = shiftAssignmentRepository;
        this.attendanceRepository = attendanceRepository;
        this.shiftRepository = shiftRepository;
        this.employeeRepository = employeeRepository;
    }

    // Get shift calendar for an employee
    public List<ShiftCalendarDTO> getShiftCalendar(Long empId, LocalDate start, LocalDate end) {
        List<ShiftAssignment> shifts = shiftAssignmentRepository.findByEmployee_IdAndDateBetween(empId, start, end);
        return shifts.stream().map(shift -> {
            ShiftCalendarDTO dto = new ShiftCalendarDTO();
            dto.setDate(shift.getDate());
            dto.setEmployeeId(empId);
            dto.setShiftName(shift.getShift().getName());
            dto.setShiftTiming(shift.getShift().getStartTime() + " - " + shift.getShift().getEndTime());
            return dto;
        }).collect(Collectors.toList());
    }

    // Get attendance calendar for an employee
    public List<AttendanceCalendarDTO> getAttendanceCalendar(Long empId, LocalDate start, LocalDate end) {
        List<Attendance> list = attendanceRepository.findByEmployeeIdAndDateBetween(empId, start, end);
        return list.stream().map(att -> {
            AttendanceCalendarDTO dto = new AttendanceCalendarDTO();
            dto.setDate(att.getDate());
            dto.setPresent(att.isPresent());
            dto.setInTime(att.getInTime());
            dto.setOutTime(att.getOutTime());
            dto.setShiftName(att.getDayType() != null ? att.getDayType().toString() : "");
            return dto;
        }).collect(Collectors.toList());
    }

    // Get all shift assignments within a date range
    public List<ShiftCalendarDTO> getShiftCalendarData(LocalDate fromDate, LocalDate toDate) {
        List<ShiftAssignment> assignments = shiftAssignmentRepository.findByDateBetween(fromDate, toDate);
        return assignments.stream().map(assignment -> {
            ShiftCalendarDTO dto = new ShiftCalendarDTO();
            Shift shift = assignment.getShift();
            dto.setDate(assignment.getDate());
            dto.setEmployeeId(assignment.getEmployee().getId());
            dto.setShiftName(shift.getName());
            dto.setShiftTiming(shift.getStartTime() + " - " + shift.getEndTime());
            return dto;
        }).collect(Collectors.toList());
    }

    // Save a new shift assignment
    public void saveShift(ShiftCalendarDTO dto) {
        if (dto.getEmployeeId() == null || dto.getShiftName() == null || dto.getDate() == null) {
            throw new IllegalArgumentException("Missing required fields: employeeId, shiftName, or date");
        }

        Employee employee = employeeRepository.findById(dto.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        Shift shift = shiftRepository.findByName(dto.getShiftName())
                .orElseThrow(() -> new RuntimeException("Shift not found: " + dto.getShiftName()));

        // Check if assignment already exists for the same date and employee
        boolean exists = shiftAssignmentRepository.existsByEmployee_IdAndDate(dto.getEmployeeId(), dto.getDate());
        if (exists) {
            throw new RuntimeException("Shift already assigned for this date and employee");
        }

        ShiftAssignment assignment = new ShiftAssignment();
        assignment.setDate(dto.getDate());
        assignment.setEmployee(employee);
        assignment.setShift(shift);

        shiftAssignmentRepository.save(assignment);
    }
}
