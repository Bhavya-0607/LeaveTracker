package com.example.demo.service;

import com.example.demo.Dto.LeaveApplicationDTO;
import com.example.demo.Dto.LeaveFilterDTO;
import com.example.demo.Dto.LeaveSummaryDto;
import com.example.demo.entity.Employee;
import com.example.demo.entity.LeaveApplication;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.LeaveApplicationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LeaveApplicationServiceTest {

    private LeaveApplicationRepository leaveApplicationRepository;
    private EmployeeRepository employeeRepository;
    private LeaveApplicationService service;

    @BeforeEach
    void setup() {
        leaveApplicationRepository = mock(LeaveApplicationRepository.class);
        employeeRepository = mock(EmployeeRepository.class);
        service = new LeaveApplicationService(leaveApplicationRepository, employeeRepository);
    }

    @Test
    void testApplyLeave() {
        LeaveApplicationDTO dto = new LeaveApplicationDTO();
        dto.setEmployeeId(1L);
        dto.setLeaveType("SICK");
        dto.setFromDate(LocalDate.of(2024, 1, 1));
        dto.setToDate(LocalDate.of(2024, 1, 2));
        dto.setReason("Fever");
        dto.setHoursTaken(8);
        dto.setRequestDate(LocalDate.of(2023, 12, 31));
        dto.setOperation("Apply");
        dto.setTeamEmailId("team@example.com");

        Employee emp = new Employee();
        emp.setId(1L);

        LeaveApplication leave = new LeaveApplication();
        leave.setId(10L);
        leave.setLeaveType(dto.getLeaveType());
        leave.setFromDate(dto.getFromDate());
        leave.setToDate(dto.getToDate());
        leave.setReason(dto.getReason());
        leave.setHoursTaken(dto.getHoursTaken());
        leave.setRequestDate(dto.getRequestDate());
        leave.setOperation(dto.getOperation());
        leave.setTeamEmailId(dto.getTeamEmailId());
        leave.setEmployee(emp);

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(emp));
        when(leaveApplicationRepository.save(Mockito.any(LeaveApplication.class))).thenReturn(leave);

        LeaveApplicationDTO result = service.applyLeave(dto);

        assertNotNull(result);
        assertEquals(10L, result.getId());
    }

    @Test
    void testGetAllApplications() {
        LeaveApplication leave = new LeaveApplication();
        leave.setId(1L);
        leave.setLeaveType("CASUAL");
        leave.setFromDate(LocalDate.of(2024, 4, 1));
        leave.setToDate(LocalDate.of(2024, 4, 2));
        leave.setReason("Trip");
        leave.setHoursTaken(16);
        leave.setRequestDate(LocalDate.of(2024, 3, 30));
        leave.setOperation("Apply");
        leave.setTeamEmailId("team@example.com");

        Employee emp = new Employee();
        emp.setId(2L);
        leave.setEmployee(emp);

        when(leaveApplicationRepository.findAll()).thenReturn(List.of(leave));

        List<LeaveApplicationDTO> result = service.getAllApplications();
        assertEquals(1, result.size());
        assertEquals("CASUAL", result.get(0).getLeaveType());
    }

    @Test
    void testGetLeaveSummary() {
        Employee emp = new Employee();
        emp.setId(1L);

        LeaveApplication leave1 = new LeaveApplication();
        leave1.setEmployee(emp);

        LeaveApplication leave2 = new LeaveApplication();
        leave2.setEmployee(emp);

        List<LeaveApplication> mockApps = new ArrayList<>();
        mockApps.add(leave1);
        mockApps.add(leave2);

        when(leaveApplicationRepository.findAll()).thenReturn(mockApps);

        LeaveSummaryDto summary = service.getLeaveSummary(1L);

        assertEquals(2, summary.getBookedLeaves());
        assertEquals(18, summary.getAvailableLeaves());
    }

    @Test
    void testFilterLeaves() {
        LeaveApplication leave = new LeaveApplication();
        leave.setLeaveType("SICK");
        leave.setFromDate(LocalDate.of(2024, 5, 1));
        leave.setToDate(LocalDate.of(2024, 5, 2));

        Employee emp = new Employee();
        emp.setId(1L);
        leave.setEmployee(emp);

        when(leaveApplicationRepository.findAll()).thenReturn(List.of(leave));

        LeaveFilterDTO filter = new LeaveFilterDTO();
        filter.setFromDate(LocalDate.of(2024, 4, 30));
        filter.setToDate(LocalDate.of(2024, 5, 3));
        filter.setLeaveType("SICK");

        List<LeaveApplicationDTO> result = service.filterLeaves(filter);
        assertEquals(1, result.size());
        assertEquals("SICK", result.get(0).getLeaveType());
    }
}
