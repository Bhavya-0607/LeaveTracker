package com.example.demo.service;

import com.example.demo.Dto.AttendanceCalendarDTO;
import com.example.demo.Dto.ShiftCalendarDTO;
import com.example.demo.entity.Attendance;
import com.example.demo.entity.Shift;
import com.example.demo.entity.ShiftAssignment;
import com.example.demo.repository.AttendanceRepository;
import com.example.demo.repository.ShiftAssignmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ShiftServiceTest {

    private AttendanceRepository attendanceRepository;
    private ShiftAssignmentRepository shiftAssignmentRepository;
    private ShiftService shiftService;

    @BeforeEach
    public void setUp() {
        attendanceRepository = mock(AttendanceRepository.class);
        shiftAssignmentRepository = mock(ShiftAssignmentRepository.class);
        shiftService = new ShiftService(shiftAssignmentRepository, attendanceRepository);
    }

    @Test
    public void testGetShiftCalendar() {
        Long empId = 1L;
        LocalDate start = LocalDate.of(2024, 6, 1);
        LocalDate end = LocalDate.of(2024, 6, 10);

        Shift shift = new Shift();
        shift.setName("Morning");
        shift.setStartTime(LocalTime.of(9, 0));
        shift.setEndTime(LocalTime.of(17, 0));

        ShiftAssignment assignment = new ShiftAssignment();
        assignment.setDate(LocalDate.of(2024, 6, 3));
        assignment.setShift(shift);

        when(shiftAssignmentRepository.findByEmployee_IdAndDateBetween(empId, start, end))
                .thenReturn(Collections.singletonList(assignment));

        List<ShiftCalendarDTO> result = shiftService.getShiftCalendar(empId, start, end);

        assertEquals(1, result.size());
        assertEquals("Morning", result.get(0).getShiftName());
        assertEquals("09:00 - 17:00", result.get(0).getShiftTiming());
    }

    @Test
    public void testGetAttendanceCalendar() {
        Long empId = 1L;
        LocalDate start = LocalDate.of(2024, 6, 1);
        LocalDate end = LocalDate.of(2024, 6, 10);

        Attendance attendance = new Attendance();
        attendance.setDate(LocalDate.of(2024, 6, 2));
        attendance.setPresent(true);
        attendance.setInTime(LocalTime.of(9, 5));
        attendance.setOutTime(LocalTime.of(17, 1));
        attendance.setDayType(Attendance.DayType.REGULAR);

        when(attendanceRepository.findByEmployeeIdAndDateBetween(empId, start, end))
                .thenReturn(Collections.singletonList(attendance));

        List<AttendanceCalendarDTO> result = shiftService.getAttendanceCalendar(empId, start, end);

        assertEquals(1, result.size());
        assertTrue(result.get(0).isPresent());
        assertEquals("REGULAR", result.get(0).getShiftName());
    }

    @Test
    public void testGetShiftCalendarData_ReturnsEmptyList() {
        List<ShiftCalendarDTO> result = shiftService.getShiftCalendarData(LocalDate.now(), LocalDate.now().plusDays(1));
        assertTrue(result.isEmpty());
    }
}
