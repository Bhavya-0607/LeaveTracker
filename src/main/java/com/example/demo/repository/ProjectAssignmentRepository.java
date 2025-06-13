package com.example.demo.repository;

import com.example.demo.entity.ProjectAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectAssignmentRepository extends JpaRepository<ProjectAssignment, Long> {
	 ProjectAssignment findByEmployeeIdAndStatus(Long employeeId, String status);

    // Additional query methods if needed
}
