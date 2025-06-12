package com.example.demo.controller;

import com.example.demo.Dto.LeaveApplicationDTO;
import com.example.demo.service.LeaveApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leave-applications")

public class LeaveApplicationController {

    private final LeaveApplicationService leaveApplicationService;
   
    public LeaveApplicationController(LeaveApplicationService leaveApplicationService) {
        this.leaveApplicationService = leaveApplicationService;
    }


    @PostMapping
    public LeaveApplicationDTO applyLeave(@RequestBody LeaveApplicationDTO dto) {
        return leaveApplicationService.applyLeave(dto);
    }

    @GetMapping
    public List<LeaveApplicationDTO> getAllApplications() {
        return leaveApplicationService.getAllApplications();
    }

    @GetMapping("/{id}")
    public LeaveApplicationDTO getById(@PathVariable Long id) {
        return leaveApplicationService.getById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        leaveApplicationService.deleteApplication(id);
    }
}
