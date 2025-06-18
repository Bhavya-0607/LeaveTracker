package com.example.demo.service;

import com.example.demo.Dto.LeaveBalanceDto;
import com.example.demo.entity.LeaveBalance;
import com.example.demo.repository.LeaveBalanceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LeaveBalanceService {

    private final LeaveBalanceRepository leaveBalanceRepository;

    public LeaveBalanceService(LeaveBalanceRepository leaveBalanceRepository) {
        this.leaveBalanceRepository = leaveBalanceRepository;
    }

    // Get all leave balances
    public List<LeaveBalanceDto> getAllLeaveBalances() {
        return leaveBalanceRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // Get leave balances for a specific user
    public List<LeaveBalanceDto> getBalancesByUser(Long userId) {
        return leaveBalanceRepository.findByEmployeeId(userId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // Create or update a leave balance
    public LeaveBalanceDto createOrUpdateLeaveBalance(LeaveBalanceDto dto) {
        LeaveBalance entity = dto.getId() != null
                ? leaveBalanceRepository.findById(dto.getId()).orElse(new LeaveBalance())
                : new LeaveBalance();

        entity.setEmployeeId(dto.getEmployeeId());
        entity.setLeaveType(dto.getLeaveType());
        entity.setAvailableLeaves(dto.getAvailableLeaves());
        entity.setBookedLeaves(dto.getBookedLeaves());

        // ✅ Calculate totalLeaves from booked + available
        if (dto.getAvailableLeaves() != null && dto.getBookedLeaves() != null) {
            int total = dto.getAvailableLeaves().intValue() + dto.getBookedLeaves().intValue();
            entity.setTotalLeaves(total);
        } else {
            entity.setTotalLeaves(0); // default/fallback
        }

        LeaveBalance saved = leaveBalanceRepository.save(entity);
        return toDto(saved);
    }

    // Get leave balance by ID
    public LeaveBalanceDto getLeaveBalanceById(Long id) {
        LeaveBalance balance = leaveBalanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave balance not found with ID: " + id));
        return toDto(balance);
    }

    // Delete leave balance
    public void deleteLeaveBalance(Long id) {
        if (!leaveBalanceRepository.existsById(id)) {
            throw new RuntimeException("Leave balance not found with ID: " + id);
        }
        leaveBalanceRepository.deleteById(id);
    }

    // Convert Entity to DTO
    private LeaveBalanceDto toDto(LeaveBalance entity) {
        return LeaveBalanceDto.builder()
                .id(entity.getId())
                .employeeId(entity.getEmployeeId())
                .leaveType(entity.getLeaveType())
                .availableLeaves(entity.getAvailableLeaves())
                .bookedLeaves(entity.getBookedLeaves())
                .totalLeaves(entity.getTotalLeaves()) // Include total in response if desired
                .build();
    }
}
