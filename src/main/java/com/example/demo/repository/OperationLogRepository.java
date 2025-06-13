package com.example.demo.repository;

import com.example.demo.entity.OperationLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OperationLogRepository extends JpaRepository<OperationLog, Long> {

    List<OperationLog> findByModuleNameOrderByTimestampDesc(String moduleName);

    List<OperationLog> findByPerformedByOrderByTimestampDesc(String performedBy);

    List<OperationLog> findByEmployeeIdAndTimestampBetween(Long employeeId, LocalDateTime start, LocalDateTime end);
}
