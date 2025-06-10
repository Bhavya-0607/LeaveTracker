package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "shift")
public class Shift {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // e.g., "Morning Shift"
    private LocalTime startTime;
    private LocalTime endTime;
    private boolean isDefault;
    private String description;
    private String dayType;

    // No-args constructor
    public Shift() {}

    // All-args constructor
    public Shift(Long id, String name, LocalTime startTime, LocalTime endTime, boolean isDefault, String description, String dayType) {
        this.id = id;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isDefault = isDefault;
        this.description = description;
        this.dayType = dayType;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDayType() {
        return dayType;
    }

    public void setDayType(String dayType) {
        this.dayType = dayType;
    }

    // Optional Builder
    public static class Builder {
        private Long id;
        private String name;
        private LocalTime startTime;
        private LocalTime endTime;
        private boolean isDefault;
        private String description;
        private String dayType;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder startTime(LocalTime startTime) {
            this.startTime = startTime;
            return this;
        }

        public Builder endTime(LocalTime endTime) {
            this.endTime = endTime;
            return this;
        }

        public Builder isDefault(boolean isDefault) {
            this.isDefault = isDefault;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder dayType(String dayType) {
            this.dayType = dayType;
            return this;
        }

        public Shift build() {
            return new Shift(id, name, startTime, endTime, isDefault, description, dayType);
        }
    }
}
