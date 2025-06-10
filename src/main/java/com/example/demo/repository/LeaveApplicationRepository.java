package com.example.demo.repository;

import com.example.demo.entity.LeaveApplication;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaveApplicationRepository extends JpaRepository<LeaveApplication, Long> {
	
	 List<LeaveApplication> findByEmployee_Id(Long employeeId);

}
