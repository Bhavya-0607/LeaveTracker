package com.example.demo.entity;

    

import com.example.demo.enums.LeaveStatus;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter

public class LeaveRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "leave_type_id") 
    
    private LeaveType leaveType;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    private String teamEmailId;

    @Column(length = 500)
    private String reason;

    private LocalDate createdAt;
    @Enumerated(EnumType.STRING)
    private LeaveStatus status;

    // Add setter
    public void setStatus(LeaveStatus status) {
        this.status = status;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Getter and Setter for status
    public LeaveStatus getStatus() {
        return status;
    }

   
}


