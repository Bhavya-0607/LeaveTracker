package com.example.demo.service;

import com.example.demo.Dto.TeamMemberSummaryDTO;
import com.example.demo.entity.Employee;
import com.example.demo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeamService {

    private final EmployeeRepository employeeRepository;
    private final ProjectAssignmentService projectAssignmentService;
    private final LeaveRequestService leaveRequestService;
    private final OperationLogService timeLogService;

    @Autowired
    public TeamService(EmployeeRepository employeeRepository,
                       ProjectAssignmentService projectAssignmentService,
                       LeaveRequestService leaveRequestService,
                       OperationLogService timeLogService) {
        this.employeeRepository = employeeRepository;
        this.projectAssignmentService = projectAssignmentService;
        this.leaveRequestService = leaveRequestService;
        this.timeLogService = timeLogService;
    }

    /**
     * Fetch team members under a manager with summary details.
     */
    public List<TeamMemberSummaryDTO> getTeamMembersByManagerId(Long managerId) {
        List<Employee> employees = employeeRepository.findByManagerId(managerId);
        List<TeamMemberSummaryDTO> result = new ArrayList<>();

        for (Employee employee : employees) {
            TeamMemberSummaryDTO dto = new TeamMemberSummaryDTO();

            dto.setEmployeeId(employee.getId());
            dto.setName(employee.getName());

            String projectName = projectAssignmentService.getCurrentProjectNameByEmployeeId(employee.getId());
            dto.setCurrentJob(projectName != null ? projectName : "Unassigned");

            boolean onLeave = leaveRequestService.isEmployeeOnLeave(employee.getId());
            dto.setOnLeave(onLeave);

            double hours = timeLogService.getTotalLoggedHoursThisWeek(employee.getId());
            dto.setWeeklyLoggedHours(hours);

            dto.setStatus(employee.getStatus());

            result.add(dto);
        }

        return result;
    }
}
