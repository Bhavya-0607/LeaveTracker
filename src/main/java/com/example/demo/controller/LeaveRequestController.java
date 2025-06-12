
package com.example.demo.controller;

import com.example.demo.entity.LeaveRequest;
import com.example.demo.enums.LeaveStatus;
import com.example.demo.service.LeaveImportService;
import com.example.demo.service.LeaveRequestService;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.example.demo.service.LeaveRequestService;

@RestController
@RequestMapping("/api/leaves")

public class LeaveRequestController {
	
	

	private LeaveRequestService leaveRequestService;



    

    // POST: Apply for leave
    @PostMapping("/apply")
    public ResponseEntity<LeaveRequest> applyLeave(@RequestBody LeaveRequest leaveRequest) {
        LeaveRequest createdRequest = leaveRequestService.applyLeave(leaveRequest);
        return ResponseEntity.ok(createdRequest);
    }

    // GET: Get leave requests by userId
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<LeaveRequest>> getLeaveRequestsByUserId(@PathVariable Long userId) {
        List<LeaveRequest> requests = leaveRequestService.getLeaveRequestsByUserId(userId);
        return ResponseEntity.ok(requests);
    }

    // PUT: Approve leave request
    @PutMapping("/{id}/approve")
    public ResponseEntity<LeaveRequest> approveLeave(@PathVariable Long id) {
        LeaveRequest updatedRequest = leaveRequestService.updateLeaveStatus(id, LeaveStatus.APPROVED);
        return ResponseEntity.ok(updatedRequest);
    }

    // PUT: Reject leave request
    @PutMapping("/{id}/reject")
    public ResponseEntity<LeaveRequest> rejectLeave(@PathVariable Long id) {
        LeaveRequest updatedRequest = leaveRequestService.updateLeaveStatus(id, LeaveStatus.REJECTED);
        return ResponseEntity.ok(updatedRequest);
    }
}
