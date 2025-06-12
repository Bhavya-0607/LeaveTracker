package com.example.demo.service;

import com.example.demo.Dto.TeamMemberSummaryDTO;
import com.example.demo.entity.Employee;
import com.example.demo.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TeamServiceTest {

    private EmployeeRepository employeeRepository;
    private ProjectAssignmentService projectAssignmentService;
    private LeaveRequestService leaveRequestService;
    private TimeLogService timeLogService;
    private TeamService teamService;

    @BeforeEach
    public void setUp() {
        employeeRepository = mock(EmployeeRepository.class);
        projectAssignmentService = mock(ProjectAssignmentService.class);
        leaveRequestService = mock(LeaveRequestService.class);
        timeLogService = mock(TimeLogService.class);

        teamService = new TeamServiceImpl(
                employeeRepository,
                projectAssignmentService,
                leaveRequestService,
                timeLogService
        );
    }

    @Test
    public void testGetTeamMembersByManagerId_Success() {
        // Arrange
        Employee emp1 = new Employee();
        emp1.setId(1L);
        emp1.setName("Alice");
        emp1.setStatus("Active"); // ✅ fixed

        Employee emp2 = new Employee();
        emp2.setId(2L);
        emp2.setName("Bob");
        emp2.setStatus("Inactive"); // ✅ fixed

        when(employeeRepository.findByManagerId(101L)).thenReturn(Arrays.asList(emp1, emp2));

        when(projectAssignmentService.getCurrentProjectNameByEmployeeId(1L)).thenReturn("Project A");
        when(projectAssignmentService.getCurrentProjectNameByEmployeeId(2L)).thenReturn(null);

        when(leaveRequestService.isEmployeeOnLeave(1L)).thenReturn(false);
        when(leaveRequestService.isEmployeeOnLeave(2L)).thenReturn(true);

        when(timeLogService.getTotalLoggedHoursThisWeek(1L)).thenReturn(38.5);
        when(timeLogService.getTotalLoggedHoursThisWeek(2L)).thenReturn(25.0);

        // Act
        List<TeamMemberSummaryDTO> result = teamService.getTeamMembersByManagerId(101L);

        // Assert
        assertEquals(2, result.size());

        TeamMemberSummaryDTO alice = result.get(0);
        assertEquals(1L, alice.getEmployeeId());
        assertEquals("Alice", alice.getName());
        assertEquals("Project A", alice.getCurrentJob());
        assertFalse(alice.isOnLeave());
        assertEquals(38.5, alice.getWeeklyLoggedHours());
        assertEquals("Active", alice.getStatus()); // ✅ fixed

        TeamMemberSummaryDTO bob = result.get(1);
        assertEquals(2L, bob.getEmployeeId());
        assertEquals("Bob", bob.getName());
        assertEquals("Unassigned", bob.getCurrentJob());
        assertTrue(bob.isOnLeave());
        assertEquals(25.0, bob.getWeeklyLoggedHours());
        assertEquals("Inactive", bob.getStatus()); // ✅ fixed
    }
}
