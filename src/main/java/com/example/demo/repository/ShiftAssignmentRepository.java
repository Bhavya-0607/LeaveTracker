package com.example.demo.repository;

import com.example.demo.entity.ShiftAssignment;
import com.example.demo.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository for performing CRUD operations on ShiftAssignment entities.
 */
@Repository
public interface ShiftAssignmentRepository extends JpaRepository<ShiftAssignment, Long> {

    /**
     * Fetch all shift assignments for a given employee ID within a specified date range.
     *
     * @param employeeId the ID of the employee
     * @param start the start date
     * @param end the end date
     * @return list of ShiftAssignment entities
     */
    List<ShiftAssignment> findByEmployee_IdAndDateBetween(Long employeeId, LocalDate start, LocalDate end);

    /**
     * Optional: Fetch all shift assignments for a given Employee entity within a specified date range.
     *
     * @param employee the Employee entity
     * @param start the start date
     * @param end the end date
     * @return list of ShiftAssignment entities
     */
    List<ShiftAssignment> findByEmployeeAndDateBetween(Employee employee, LocalDate start, LocalDate end);

    /**
     * Fetch all assignments between two dates (for all employees).
     */
    List<ShiftAssignment> findByDateBetween(LocalDate from, LocalDate to);

    /**
     * ✅ Check if a shift assignment already exists for a specific employee on a given date.
     */
    boolean existsByEmployee_IdAndDate(Long employeeId, LocalDate date);
}
