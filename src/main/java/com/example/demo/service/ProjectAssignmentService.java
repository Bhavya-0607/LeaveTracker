package com.example.demo.service;

import com.example.demo.entity.ProjectAssignment;
import com.example.demo.repository.ProjectAssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectAssignmentService {

    private final ProjectAssignmentRepository repository;

    @Autowired
    public ProjectAssignmentService(ProjectAssignmentRepository repository) {
        this.repository = repository;
    }

    public ProjectAssignment createAssignment(ProjectAssignment assignment) {
        return repository.save(assignment);
    }

    public List<ProjectAssignment> getAllAssignments() {
        return repository.findAll();
    }

    public ProjectAssignment getAssignmentById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void deleteAssignment(Long id) {
        repository.deleteById(id);
    }
    public String getCurrentProjectNameByEmployeeId(long employeeId) {
        // Sample implementation
        ProjectAssignment assignment = repository.findByEmployeeIdAndStatus(employeeId, "ACTIVE");
        return (assignment != null) ? "Project-" + assignment.getProjectId() : null;
    }

}
