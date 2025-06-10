package com.example.demo.service;

import com.example.demo.entity.LeaveRequest;
import com.example.demo.enums.LeaveStatus;
import com.example.demo.repository.LeaveRequestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class LeaveRequestServiceTest {

    private LeaveRequestRepository leaveRequestRepository;
    private LeaveRequestService leaveRequestService;

    @BeforeEach
    void setUp() {
        leaveRequestRepository = mock(LeaveRequestRepository.class);
        leaveRequestService = new LeaveRequestService();
        // Use reflection to inject the mock (since @Autowired isn't usable here)
        injectRepository(leaveRequestService, leaveRequestRepository);
    }

    @Test
    void testApplyLeave_setsStatusToPendingAndSaves() {
        LeaveRequest request = new LeaveRequest();
        when(leaveRequestRepository.save(any())).thenReturn(request);

        LeaveRequest result = leaveRequestService.applyLeave(request);
        assertEquals(LeaveStatus.PENDING, result.getStatus());
        verify(leaveRequestRepository).save(request);
    }

    @Test
    void testGetLeaveRequestsByUserId_returnsList() {
        LeaveRequest request = new LeaveRequest();
        when(leaveRequestRepository.findByEmployee_Id(1L)).thenReturn(Arrays.asList(request));

        List<LeaveRequest> result = leaveRequestService.getLeaveRequestsByUserId(1L);
        assertEquals(1, result.size());
        verify(leaveRequestRepository).findByEmployee_Id(1L);
    }

    @Test
    void testApproveLeave_setsStatusToApproved() {
        LeaveRequest request = new LeaveRequest();
        request.setId(1L);
        when(leaveRequestRepository.findById(1L)).thenReturn(Optional.of(request));
        when(leaveRequestRepository.save(any())).thenReturn(request);

        LeaveRequest result = leaveRequestService.approveLeave(1L);
        assertEquals(LeaveStatus.APPROVED, result.getStatus());
    }

    @Test
    void testRejectLeave_setsStatusToRejected() {
        LeaveRequest request = new LeaveRequest();
        request.setId(1L);
        when(leaveRequestRepository.findById(1L)).thenReturn(Optional.of(request));
        when(leaveRequestRepository.save(any())).thenReturn(request);

        LeaveRequest result = leaveRequestService.rejectLeave(1L);
        assertEquals(LeaveStatus.REJECTED, result.getStatus());
    }

    @Test
    void testGetAllLeaveRequests_returnsList() {
        LeaveRequest req1 = new LeaveRequest();
        LeaveRequest req2 = new LeaveRequest();
        when(leaveRequestRepository.findAll()).thenReturn(Arrays.asList(req1, req2));

        List<LeaveRequest> result = leaveRequestService.getAllLeaveRequests();
        assertEquals(2, result.size());
    }

    @Test
    void testUpdateLeaveStatus_setsStatusCorrectly() {
        LeaveRequest request = new LeaveRequest();
        request.setId(1L);
        when(leaveRequestRepository.findById(1L)).thenReturn(Optional.of(request));
        when(leaveRequestRepository.save(any())).thenReturn(request);

        LeaveRequest result = leaveRequestService.updateLeaveStatus(1L, LeaveStatus.REJECTED);
        assertEquals(LeaveStatus.REJECTED, result.getStatus());
    }

    @Test
    void testApproveLeave_notFound_throwsException() {
        when(leaveRequestRepository.findById(99L)).thenReturn(Optional.empty());

        Exception ex = assertThrows(RuntimeException.class, () -> leaveRequestService.approveLeave(99L));
        assertTrue(ex.getMessage().contains("not found"));
    }

    // ---------- Helper for Dependency Injection Without Lombok ----------
    private void injectRepository(LeaveRequestService service, LeaveRequestRepository repo) {
        try {
            var field = LeaveRequestService.class.getDeclaredField("leaveRequestRepository");
            field.setAccessible(true);
            field.set(service, repo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
