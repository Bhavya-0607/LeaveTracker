package com.example.demo.service;

import com.example.demo.Dto.LeaveBalanceDto;
import com.example.demo.entity.LeaveBalance;
import com.example.demo.repository.LeaveBalanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LeaveBalanceService {

    private LeaveBalanceRepository leaveBalanceRepository;
    public LeaveBalanceService(LeaveBalanceRepository leaveBalanceRepository) {
        this.leaveBalanceRepository = leaveBalanceRepository;
    }


    public List<LeaveBalanceDto> getBalancesByUser(Long userId) {
        return leaveBalanceRepository.findByEmployeeId(userId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<LeaveBalanceDto> getAllLeaveBalances() {
        return leaveBalanceRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public LeaveBalanceDto createOrUpdateLeaveBalance(LeaveBalanceDto dto) {
        LeaveBalance entity = dto.getId() != null
                ? leaveBalanceRepository.findById(dto.getId()).orElse(new LeaveBalance())
                : new LeaveBalance();

        entity.setEmployeeId(dto.getEmployeeId());
        entity.setLeaveType(dto.getLeaveType());
        entity.setAvailableLeaves(dto.getAvailableLeaves());
        entity.setBookedLeaves(dto.getBookedLeaves());

        LeaveBalance saved = leaveBalanceRepository.save(entity);
        return toDto(saved);
    }

    public LeaveBalanceDto getLeaveBalanceById(Long id) {
        LeaveBalance balance = leaveBalanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave balance not found with ID: " + id));
        return toDto(balance);
    }

    public void deleteLeaveBalance(Long id) {
        if (!leaveBalanceRepository.existsById(id)) {
            throw new RuntimeException("Leave balance not found with ID: " + id);
        }
        leaveBalanceRepository.deleteById(id);
    }

    // --- DTO Mapper Method ---
    private LeaveBalanceDto toDto(LeaveBalance entity) {
        return LeaveBalanceDto.builder()
                .id(entity.getId())
                .employeeId(entity.getEmployeeId())
                .leaveType(entity.getLeaveType())
                .availableLeaves(entity.getAvailableLeaves())
                .bookedLeaves(entity.getBookedLeaves())
                .build();
    }
}


