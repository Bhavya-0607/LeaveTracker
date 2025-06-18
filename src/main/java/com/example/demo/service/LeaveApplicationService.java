package com.example.demo.service;

import com.example.demo.Dto.LeaveApplicationDTO;
import com.example.demo.Dto.LeaveFilterDTO;
import com.example.demo.Dto.LeaveSummaryDto;
import com.example.demo.entity.Employee;
import com.example.demo.entity.LeaveApplication;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.LeaveApplicationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LeaveApplicationService {

    private final LeaveApplicationRepository leaveApplicationRepository;
    private final EmployeeRepository employeeRepository;

    public LeaveApplicationService(LeaveApplicationRepository leaveApplicationRepository,
                                   EmployeeRepository employeeRepository) {
        this.leaveApplicationRepository = leaveApplicationRepository;
        this.employeeRepository = employeeRepository;
    }

    public LeaveApplicationDTO applyLeave(LeaveApplicationDTO dto) {
        Employee employee = employeeRepository.findById(dto.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + dto.getEmployeeId()));

        LeaveApplication leave = LeaveApplication.builder()
                .leaveType(dto.getLeaveType())
                .fromDate(dto.getFromDate())
                .toDate(dto.getToDate())
                .reason(dto.getReason())
                .status("PENDING")
                .hoursTaken(dto.getHoursTaken())
                .requestDate(dto.getRequestDate())
                .operation(dto.getOperation())
                .teamEmailId(dto.getTeamEmailId())
                .employee(employee)
                .build();

        LeaveApplication saved = leaveApplicationRepository.save(leave);
        dto.setId(saved.getId());
        return dto;
    }

    public List<LeaveApplicationDTO> getAllApplications() {
        return leaveApplicationRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public LeaveApplicationDTO getById(Long id) {
        LeaveApplication l = leaveApplicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave application not found with ID: " + id));
        return convertToDTO(l);
    }

    public void deleteApplication(Long id) {
        leaveApplicationRepository.deleteById(id);
    }

    private LeaveApplicationDTO convertToDTO(LeaveApplication l) {
        LeaveApplicationDTO dto = new LeaveApplicationDTO();
        dto.setId(l.getId());
        dto.setLeaveType(l.getLeaveType());
        dto.setFromDate(l.getFromDate());
        dto.setToDate(l.getToDate());
        dto.setReason(l.getReason());
        dto.setHoursTaken(l.getHoursTaken());
        dto.setRequestDate(l.getRequestDate());
        dto.setOperation(l.getOperation());
        dto.setTeamEmailId(l.getTeamEmailId());
        dto.setEmployeeId(l.getEmployee().getId());
        return dto;
    }

    public List<LeaveApplicationDTO> filterLeaves(LeaveFilterDTO filterDTO) {
        return leaveApplicationRepository.findAll()
                .stream()
                .filter(l -> {
                    if (filterDTO.getFromDate() != null && l.getFromDate().isBefore(filterDTO.getFromDate())) return false;
                    if (filterDTO.getToDate() != null && l.getToDate().isAfter(filterDTO.getToDate())) return false;
                    if (filterDTO.getLeaveType() != null && !l.getLeaveType().equalsIgnoreCase(filterDTO.getLeaveType())) return false;
                    return true;
                })
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public LeaveSummaryDto getLeaveSummary(Long employeeId) {
        List<LeaveApplication> applications = leaveApplicationRepository.findAll()
                .stream()
                .filter(app -> app.getEmployee().getId().equals(employeeId))
                .collect(Collectors.toList());

        LeaveSummaryDto summary = new LeaveSummaryDto();
        summary.setBookedLeaves(applications.size());
        summary.setAvailableLeaves(20 - applications.size());

        return summary;
    }
}
