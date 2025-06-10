package com.example.demo.entity;
import java.time.LocalDate;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;




@Entity
@Table(name = "shift_assignments")

public class ShiftAssignment {
	  @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @ManyToOne
	    @JoinColumn(name = "employee_id", nullable = false)
	    private Employee employee;

    @ManyToOne
    private Shift shift;

    private LocalDate date; // specific day shift is assigned for
    public LocalDate getDate() {
        return date;
    }

    public Shift getShift() {
        return shift;
    }
    
    private Long employeeId;


    public void setDate(LocalDate date) {
        this.date = date;
    }


    public void setShift(Shift shift) {
        this.shift = shift;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

}
