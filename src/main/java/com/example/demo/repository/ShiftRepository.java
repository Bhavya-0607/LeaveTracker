package com.example.demo.repository;

import com.example.demo.entity.Shift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.Optional;

/**
 * Repository for performing CRUD operations on Shift entities.
 */
@Repository
public interface ShiftRepository extends JpaRepository<Shift, Long> {

    /**
     * Finds a shift by its name.
     *
     * @param name the name of the shift
     * @return an optional Shift
     */
    Optional<Shift> findByName(String name);

    /**
     * Finds shifts by exact start and end time.
     *
     * @param startTime the start time of the shift
     * @param endTime the end time of the shift
     * @return a list of matching Shift entities
     */
    Optional<Shift> findByStartTimeAndEndTime(LocalTime startTime, LocalTime endTime);
}
