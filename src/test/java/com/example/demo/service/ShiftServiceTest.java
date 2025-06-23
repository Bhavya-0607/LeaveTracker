package com.example.demo.service;

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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ShiftServiceTest {

    private ShiftAssignmentRepository shiftAssignmentRepository;
    private AttendanceRepository attendanceRepository;
    private ShiftRepository shiftRepository;
    private EmployeeRepository employeeRepository;
    private ShiftService shiftService;

    @BeforeEach
    public void setUp() {
        shiftAssignmentRepository = mock(ShiftAssignmentRepository.class);
        attendanceRepository = mock(AttendanceRepository.class);
        shiftRepository = mock(ShiftRepository.class);
        employeeRepository = mock(EmployeeRepository.class);

        shiftService = new ShiftService(
                shiftAssignmentRepository,
                attendanceRepository,
                shiftRepository,
                employeeRepository
        );
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

        Employee employee = new Employee();
        employee.setId(empId);

        ShiftAssignment assignment = new ShiftAssignment();
        assignment.setDate(LocalDate.of(2024, 6, 3));
        assignment.setShift(shift);
        assignment.setEmployee(employee);

        when(shiftAssignmentRepository.findByEmployee_IdAndDateBetween(empId, start, end))
                .thenReturn(Collections.singletonList(assignment));

        List<ShiftCalendarDTO> result = shiftService.getShiftCalendar(empId, start, end);

        assertEquals(1, result.size());
        assertEquals("Morning", result.get(0).getShiftName());
        assertEquals("09:00 - 17:00", result.get(0).getShiftTiming());
        assertEquals(empId, result.get(0).getEmployeeId());
    }

    @Test
    public void testGetAttendanceCalendar() {
        Long empId = 1L;
        LocalDate start = LocalDate.of(2024, 6, 1);
        LocalDate end = LocalDate.of(2024, 6, 10);

        Attendance attendance = new Attendance();
        attendance.setDate(LocalDate.of(2024, 6, 2));
        attendance.setPresent(true);
        attendance.setInTime(LocalTime.of(9, 0));
        attendance.setOutTime(LocalTime.of(17, 0));
        attendance.setDayType(Attendance.DayType.REGULAR);

        when(attendanceRepository.findByEmployeeIdAndDateBetween(empId, start, end))
                .thenReturn(Collections.singletonList(attendance));

        List<AttendanceCalendarDTO> result = shiftService.getAttendanceCalendar(empId, start, end);

        assertEquals(1, result.size());
        assertTrue(result.get(0).isPresent());
        assertEquals("REGULAR", result.get(0).getShiftName());
    }

    @Test
    public void testSaveShift_Success() {
        Long empId = 1L;
        String shiftName = "Morning";
        LocalDate date = LocalDate.of(2024, 6, 5);

        Shift shift = new Shift();
        shift.setName(shiftName);

        Employee employee = new Employee();
        employee.setId(empId);

        ShiftCalendarDTO dto = new ShiftCalendarDTO();
        dto.setEmployeeId(empId);
        dto.setShiftName(shiftName);
        dto.setDate(date);

        when(employeeRepository.findById(empId)).thenReturn(Optional.of(employee));
        when(shiftRepository.findByName(shiftName)).thenReturn(Optional.of(shift));

        assertDoesNotThrow(() -> shiftService.saveShift(dto));
        verify(shiftAssignmentRepository, times(1)).save(any(ShiftAssignment.class));
    }

    @Test
    public void testGetShiftCalendarData_ReturnsEmptyList() {
        when(shiftAssignmentRepository.findByDateBetween(any(), any()))
                .thenReturn(Collections.emptyList());

        List<ShiftCalendarDTO> result = shiftService.getShiftCalendarData(LocalDate.now(), LocalDate.now().plusDays(1));
        assertTrue(result.isEmpty());
    }
}
