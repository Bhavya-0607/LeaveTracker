package com.example.demo.service;

import com.example.demo.Dto.LeaveBalanceDto;
import com.example.demo.entity.LeaveBalance;
import com.example.demo.enums.LeaveType;
import com.example.demo.repository.LeaveBalanceRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LeaveBalanceServiceTest {

    private LeaveBalanceRepository leaveBalanceRepository;
    private LeaveBalanceService leaveBalanceService;

    @BeforeEach
    public void setUp() {
        leaveBalanceRepository = mock(LeaveBalanceRepository.class);
        leaveBalanceService = new LeaveBalanceService(leaveBalanceRepository);
    }

    @Test
    public void testGetAllLeaveBalances() {
        LeaveBalance lb = createEntity(1L, 101L);
        when(leaveBalanceRepository.findAll()).thenReturn(Arrays.asList(lb));

        List<LeaveBalanceDto> result = leaveBalanceService.getAllLeaveBalances();

        assertEquals(1, result.size());
        assertEquals(101L, result.get(0).getEmployeeId());
        assertEquals(LeaveType.EARNED_LEAVE, result.get(0).getLeaveType());
    }

    @Test
    public void testGetBalancesByUser() {
        LeaveBalance lb = createEntity(1L, 101L);
        when(leaveBalanceRepository.findByEmployeeId(101L)).thenReturn(Arrays.asList(lb));

        List<LeaveBalanceDto> result = leaveBalanceService.getBalancesByUser(101L);

        assertEquals(1, result.size());
        assertEquals(LeaveType.EARNED_LEAVE, result.get(0).getLeaveType());
        assertEquals(101L, result.get(0).getEmployeeId());
    }

    @Test
    public void testCreateOrUpdateLeaveBalance_New() {
        LeaveBalanceDto dto = createDto(null, 101L);
        LeaveBalance savedEntity = createEntity(1L, 101L);

        when(leaveBalanceRepository.save(ArgumentMatchers.any())).thenReturn(savedEntity);

        LeaveBalanceDto result = leaveBalanceService.createOrUpdateLeaveBalance(dto);

        assertNotNull(result.getId());
        assertEquals(LeaveType.EARNED_LEAVE, result.getLeaveType());
    }

    @Test
    public void testCreateOrUpdateLeaveBalance_Update() {
        LeaveBalanceDto dto = createDto(1L, 101L);
        LeaveBalance existing = createEntity(1L, 101L);

        when(leaveBalanceRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(leaveBalanceRepository.save(ArgumentMatchers.any())).thenReturn(existing);

        LeaveBalanceDto result = leaveBalanceService.createOrUpdateLeaveBalance(dto);

        assertEquals(1L, result.getId());
        assertEquals(LeaveType.EARNED_LEAVE, result.getLeaveType());
    }

    @Test
    public void testGetLeaveBalanceById() {
        LeaveBalance lb = createEntity(1L, 101L);
        when(leaveBalanceRepository.findById(1L)).thenReturn(Optional.of(lb));

        LeaveBalanceDto result = leaveBalanceService.getLeaveBalanceById(1L);

        assertEquals(101L, result.getEmployeeId());
    }

    @Test
    public void testDeleteLeaveBalance() {
        when(leaveBalanceRepository.existsById(1L)).thenReturn(true);
        doNothing().when(leaveBalanceRepository).deleteById(1L);

        assertDoesNotThrow(() -> leaveBalanceService.deleteLeaveBalance(1L));
        verify(leaveBalanceRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteLeaveBalance_NotFound() {
        when(leaveBalanceRepository.existsById(1L)).thenReturn(false);

        Exception ex = assertThrows(RuntimeException.class, () -> leaveBalanceService.deleteLeaveBalance(1L));
        assertTrue(ex.getMessage().contains("not found"));
    }

    // --------- Helper Methods ---------
    private LeaveBalance createEntity(Long id, Long empId) {
        LeaveBalance lb = new LeaveBalance();
        lb.setId(id);
        lb.setEmployeeId(empId);
        lb.setLeaveType(LeaveType.EARNED_LEAVE);
        lb.setAvailableLeaves(5.0);
        lb.setBookedLeaves(2.0);
        return lb;
    }

    private LeaveBalanceDto createDto(Long id, Long empId) {
        LeaveBalanceDto dto = new LeaveBalanceDto();
        dto.setId(id);
        dto.setEmployeeId(empId);
        dto.setLeaveType(LeaveType.EARNED_LEAVE);
        dto.setAvailableLeaves(5.0);
        dto.setBookedLeaves(2.0);
        return dto;
    }
}
