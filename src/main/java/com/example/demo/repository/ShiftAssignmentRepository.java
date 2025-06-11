package com.example.demo.repository;

import com.example.demo.entity.ShiftAssignment;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for performing CRUD operations on ShiftAssignment entities.
 */
@Repository
public interface ShiftAssignmentRepository extends JpaRepository<ShiftAssignment, Long> {

    /**
     * Fetches all shift assignments for a given employee within a specified date range.
     *
     * @param employeeId the ID of the employee
     * @param start the start date of the range
     * @param end the end date of the range
     * @return a list of ShiftAssignment records
     */
    List<ShiftAssignment> findByEmployee_IdAndDateBetween(Long employeeId, LocalDate start, LocalDate end);
}
