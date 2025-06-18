package com.example.demo.entity;

import com.example.demo.enums.LeaveType;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "leave_balances")
public class LeaveBalance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employee_id", nullable = false)
    private Long employeeId;

    @Enumerated(EnumType.STRING)
    @Column(name = "leave_type", nullable = false)
    private LeaveType leaveType;

    @Column(name = "total_leaves", nullable = false)
    private Integer totalLeaves;

    @Column(name = "used_leaves", nullable = false)
    private Double bookedLeaves;

    @Column(name = "available_leaves", nullable = false)
    private Double availableLeaves;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public LeaveType getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(LeaveType leaveType) {
        this.leaveType = leaveType;
    }

    public Integer getTotalLeaves() {
        return totalLeaves;
    }

    public void setTotalLeaves(	Integer totalLeaves) {
        this.totalLeaves = totalLeaves;
    }

    public Double getBookedLeaves() {
        return bookedLeaves;
    }

    public void setBookedLeaves(Double bookedLeaves) {
        this.bookedLeaves = bookedLeaves;
    }

    public Double getAvailableLeaves() {
        return availableLeaves;
    }

    public void setAvailableLeaves(Double availableLeaves) {
        this.availableLeaves = availableLeaves;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
