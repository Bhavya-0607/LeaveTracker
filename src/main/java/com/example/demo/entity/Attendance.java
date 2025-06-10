package com.example.demo.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import com.example.demo.enums.DayType;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "attendances")

public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Employee employee;

    private LocalDate date;
    private LocalTime inTime;
    private LocalTime outTime;
    private boolean present;

    @Enumerated(EnumType.STRING)
    private DayType dayType;
    

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
   

    public LocalTime getInTime() { return inTime; }
    public void setInTime(LocalTime inTime) { this.inTime = inTime; }

    public LocalTime getOutTime() { return outTime; }
    public void setOutTime(LocalTime outTime) { this.outTime = outTime; }

    public boolean isPresent() { return present; }
    public void setPresent(boolean present) { this.present = present; }
   
   
    public DayType getDayType() {
        return dayType;
    }

    public void setDayType(DayType dayType) {
        this.dayType = dayType;
    }
    public enum DayType {
        REGULAR, HOLIDAY, WEEKEND
    }


    
}
   

    

