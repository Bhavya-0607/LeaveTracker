package com.example.demo.Dto;

import com.example.demo.enums.LeaveType;

public class LeaveBalanceDto {

    private Long id;
    private Long employeeId;
    private LeaveType leaveType;
    private Double availableLeaves;
    private Double bookedLeaves;
    private Integer totalLeaves; // ✅ Added field

    // Default constructor
    public LeaveBalanceDto() {}

    // Parameterized constructor
    public LeaveBalanceDto(Long id, Long employeeId, LeaveType leaveType,
                           Double availableLeaves, Double bookedLeaves, Integer totalLeaves) {
        this.id = id;
        this.employeeId = employeeId;
        this.leaveType = leaveType;
        this.availableLeaves = availableLeaves;
        this.bookedLeaves = bookedLeaves;
        this.totalLeaves = totalLeaves;
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

    public Double getAvailableLeaves() {
        return availableLeaves;
    }

    public void setAvailableLeaves(Double availableLeaves) {
        this.availableLeaves = availableLeaves;
    }

    public Double getBookedLeaves() {
        return bookedLeaves;
    }

    public void setBookedLeaves(Double bookedLeaves) {
        this.bookedLeaves = bookedLeaves;
    }

    public Integer getTotalLeaves() {
        return totalLeaves;
    }

    public void setTotalLeaves(Integer totalLeaves) {
        this.totalLeaves = totalLeaves;
    }

    @Override
    public String toString() {
        return "LeaveBalanceDto{" +
                "id=" + id +
                ", employeeId=" + employeeId +
                ", leaveType=" + leaveType +
                ", availableLeaves=" + availableLeaves +
                ", bookedLeaves=" + bookedLeaves +
                ", totalLeaves=" + totalLeaves +
                '}';
    }

    // ✅ Manual Builder Pattern
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private Long employeeId;
        private LeaveType leaveType;
        private Double availableLeaves;
        private Double bookedLeaves;
        private Integer totalLeaves;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder employeeId(Long employeeId) {
            this.employeeId = employeeId;
            return this;
        }

        public Builder leaveType(LeaveType leaveType) {
            this.leaveType = leaveType;
            return this;
        }

        public Builder availableLeaves(Double availableLeaves) {
            this.availableLeaves = availableLeaves;
            return this;
        }

        public Builder bookedLeaves(Double bookedLeaves) {
            this.bookedLeaves = bookedLeaves;
            return this;
        }

        public Builder totalLeaves(Integer totalLeaves) {
            this.totalLeaves = totalLeaves;
            return this;
        }

        public LeaveBalanceDto build() {
            return new LeaveBalanceDto(id, employeeId, leaveType, availableLeaves, bookedLeaves, totalLeaves);
        }
    }
}
