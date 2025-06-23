package com.example.demo.controller;

import com.example.demo.Dto.TeamMemberSummaryDTO;
import com.example.demo.service.TeamService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/team")
public class TeamController {

    private final TeamService teamService;

    // Explicit constructor (no Lombok)
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping("/manager/{managerId}")
    public List<TeamMemberSummaryDTO> getTeamSummary(@PathVariable Long managerId) {
        return teamService.getTeamMembersByManagerId(managerId);
    }
    @PostMapping("/member")
    public String saveTeamMember(@RequestBody TeamMemberSummaryDTO memberDto) {
        // TODO: You can call service to save the data if needed
        System.out.println("Received team member: " + memberDto);
        return "Team member data received successfully!";
    }
}
