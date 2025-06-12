package com.example.demo.repository;

import com.example.demo.entity.LeaveRequest;
import com.example.demo.enums.LeaveStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {

    // Get leave requests by employee ID
    List<LeaveRequest> findByEmployee_Id(Long userId);

    // ✅ Check if an employee is on leave on a specific day (e.g. today)
    boolean existsByEmployee_IdAndStartDateLessThanEqualAndEndDateGreaterThanEqualAndStatus(
            Long employeeId,
            LocalDate startDate,
            LocalDate endDate,
            LeaveStatus status
    );
}
