package com.example.demo.service;

import com.example.demo.Dto.TeamMemberSummaryDTO;
import com.example.demo.entity.Employee;
import com.example.demo.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

// Mocked service interfaces for additional logic
interface ProjectAssignmentService {
    String getCurrentProjectNameByEmployeeId(Long employeeId);
}


interface TimeLogService {
    double getTotalLoggedHoursThisWeek(Long employeeId);
}

// Public interface
public interface TeamService {
    List<TeamMemberSummaryDTO> getTeamMembersByManagerId(Long managerId);
}

// Business logic implementation
@Service
class TeamServiceImpl implements TeamService {

    private final EmployeeRepository employeeRepository;
    private final ProjectAssignmentService projectAssignmentService;
    private final LeaveRequestService leaveRequestService;
    private final TimeLogService timeLogService;

    public TeamServiceImpl(EmployeeRepository employeeRepository,
                           ProjectAssignmentService projectAssignmentService,
                           LeaveRequestService leaveRequestService,
                           TimeLogService timeLogService) {
        this.employeeRepository = employeeRepository;
        this.projectAssignmentService = projectAssignmentService;
        this.leaveRequestService = leaveRequestService;
        this.timeLogService = timeLogService;
    }

    @Override
    public List<TeamMemberSummaryDTO> getTeamMembersByManagerId(Long managerId) {
        List<Employee> employees = employeeRepository.findByManagerId(managerId);
        List<TeamMemberSummaryDTO> result = new ArrayList<>();

        for (Employee emp : employees) {
            TeamMemberSummaryDTO dto = new TeamMemberSummaryDTO();
            dto.setEmployeeId(emp.getId());
            dto.setName(emp.getName());

            // 1. Current job info (project name)
            String project = projectAssignmentService.getCurrentProjectNameByEmployeeId(emp.getId());
            dto.setCurrentJob(project != null ? project : "Unassigned");

            // 2. Leave status
            boolean onLeave = leaveRequestService.isEmployeeOnLeave(emp.getId());
            dto.setOnLeave(onLeave);

            // 3. Weekly logged hours
            double loggedHours = timeLogService.getTotalLoggedHoursThisWeek(emp.getId());
            dto.setWeeklyLoggedHours(loggedHours);

            // 4. Status from employee entity (assuming boolean 'active' field exists)
            dto.setStatus(emp.getStatus());
            result.add(dto);
        }

        return result;
    }
}
