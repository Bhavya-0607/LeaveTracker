package com.example.demo.repository;

import com.example.demo.entity.LeaveBalance;
import com.example.demo.enums.LeaveType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaveBalanceRepository extends JpaRepository<LeaveBalance, Long> {

    // ✅ VALID: Matches field name 'employeeId'
    List<LeaveBalance> findByEmployeeId(Long employeeId);

    // ✅ VALID: Matches 'employeeId' and enum field 'leaveType'
    List<LeaveBalance> findByEmployeeIdAndLeaveType(Long employeeId, LeaveType leaveType);
}
