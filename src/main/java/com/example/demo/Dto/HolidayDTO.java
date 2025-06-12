package com.example.demo.Dto;

import com.example.demo.enums.HolidayType;

import java.time.LocalDate;

public class HolidayDTO {

    private String name;
    private LocalDate date;
    private HolidayType type;
    private String description;
    private boolean notifyViaFeeds;
    private boolean reprocessLeave;
    private int reminderDaysBefore;
    private String applicableFor;
    private boolean shiftBased;

    // No-arg constructor
    public HolidayDTO() {
    }

    // All-arg constructor (optional)
    public HolidayDTO(String name, LocalDate date, HolidayType type, String description,
                      boolean notifyViaFeeds, boolean reprocessLeave, int reminderDaysBefore,
                      String applicableFor, boolean shiftBased) {
        this.name = name;
        this.date = date;
        this.type = type;
        this.description = description;
        this.notifyViaFeeds = notifyViaFeeds;
        this.reprocessLeave = reprocessLeave;
        this.reminderDaysBefore = reminderDaysBefore;
        this.applicableFor = applicableFor;
        this.shiftBased = shiftBased;
    }

    // Getters and Setters

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
