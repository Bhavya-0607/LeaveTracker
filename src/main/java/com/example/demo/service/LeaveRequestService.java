package com.example.demo.service;

import com.example.demo.entity.LeaveRequest;
import com.example.demo.enums.LeaveStatus;
import com.example.demo.repository.LeaveRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaveRequestService {

    @Autowired
    private LeaveRequestRepository leaveRequestRepository;

    // Saves or applies a new leave request
    public LeaveRequest applyLeave(LeaveRequest leaveRequest) {
        leaveRequest.setStatus(LeaveStatus.PENDING); // default status
        return leaveRequestRepository.save(leaveRequest);
    }

    // Retrieves leave requests for a specific user
    public List<LeaveRequest> getLeaveRequestsByUserId(Long userId) {
        return leaveRequestRepository.findByEmployee_Id(userId);
    }

    // Approves a leave request and returns the updated object
    public LeaveRequest approveLeave(Long requestId) {
        LeaveRequest leaveRequest = leaveRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Leave request not found"));
        leaveRequest.setStatus(LeaveStatus.APPROVED);
        return leaveRequestRepository.save(leaveRequest);
    }

    // Rejects a leave request and returns the updated object
    public LeaveRequest rejectLeave(Long requestId) {
        LeaveRequest leaveRequest = leaveRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Leave request not found"));
        leaveRequest.setStatus(LeaveStatus.REJECTED);
        return leaveRequestRepository.save(leaveRequest);
    }

    // Gets all leave requests
    public List<LeaveRequest> getAllLeaveRequests() {
        return leaveRequestRepository.findAll();
    }

    // Updates the status of a leave request
    public LeaveRequest updateLeaveStatus(Long leaveId, LeaveStatus status) {
        LeaveRequest leaveRequest = leaveRequestRepository.findById(leaveId)
                .orElseThrow(() -> new RuntimeException("Leave request not found"));
        leaveRequest.setStatus(status);
        return leaveRequestRepository.save(leaveRequest);
    }
}






