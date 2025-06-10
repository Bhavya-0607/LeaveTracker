package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

import com.example.demo.enums.HolidayType;

@Entity

public class Holiday {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;


    private String name;

    private LocalDate fromDate;

    private LocalDate toDate;

    private String location;

    private String shift;

    private String description;

    @Enumerated(EnumType.STRING)
    private HolidayType type; // ONBOARDING, RESTRICTED, etc.

    private boolean notifyViaFeeds;

    private boolean reprocessLeave;

    private int reminderDaysBefore;

    private String applicableFor; // e.g. "All", "Shift", "Location"

    private boolean shiftBased;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public HolidayType getType() {
        return type;
    }

    public void setType(HolidayType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isNotifyViaFeeds() {
        return notifyViaFeeds;
    }

    public void setNotifyViaFeeds(boolean notifyViaFeeds) {
        this.notifyViaFeeds = notifyViaFeeds;
    }

    public boolean isReprocessLeave() {
        return reprocessLeave;
    }

    public void setReprocessLeave(boolean reprocessLeave) {
        this.reprocessLeave = reprocessLeave;
    }

    public int getReminderDaysBefore() {
        return reminderDaysBefore;
    }

    public void setReminderDaysBefore(int reminderDaysBefore) {
        this.reminderDaysBefore = reminderDaysBefore;
    }

    public String getApplicableFor() {
        return applicableFor;
    }

    public void setApplicableFor(String applicableFor) {
        this.applicableFor = applicableFor;
    }

    public boolean isShiftBased() {
        return shiftBased;
    }

    public void setShiftBased(boolean shiftBased) {
        this.shiftBased = shiftBased;
    }

}

