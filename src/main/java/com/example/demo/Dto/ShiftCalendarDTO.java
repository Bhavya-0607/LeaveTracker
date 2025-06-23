package com.example.demo.Dto;

import java.time.LocalDate;

public class ShiftCalendarDTO {
    private LocalDate date;
    private String shiftName;
    private String shiftTiming;
    private Long employeeId; // ✅ Added field

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getShiftName() {
        return shiftName;
    }

    public void setShiftName(String shiftName) {
        this.shiftName = shiftName;
    }

    public String getShiftTiming() {
        return shiftTiming;
    }

    public void setShiftTiming(String shiftTiming) {
        this.shiftTiming = shiftTiming;
    }

    // ✅ Getter and Setter for employeeId
    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }
}
