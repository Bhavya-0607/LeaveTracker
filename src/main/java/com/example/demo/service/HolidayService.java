package com.example.demo.service;

import com.example.demo.Dto.HolidayDTO;
import com.example.demo.entity.Holiday;
import com.example.demo.enums.HolidayType;
import com.example.demo.repository.HolidayRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HolidayService {

    private static final Logger log = LoggerFactory.getLogger(HolidayService.class);

    private final HolidayRepository holidayRepository;

    // Constructor injection 
    public HolidayService(HolidayRepository holidayRepository) {
        this.holidayRepository = holidayRepository;
    }

    // Add a new holiday
    public HolidayDTO addHoliday(HolidayDTO dto) {
        Holiday holiday = new Holiday();
        holiday.setName(dto.getName());
        holiday.setDate(dto.getDate());
        holiday.setType(dto.getType());
        holiday.setDescription(dto.getDescription());
        holiday.setNotifyViaFeeds(dto.isNotifyViaFeeds());
        holiday.setReprocessLeave(dto.isReprocessLeave());
        holiday.setReminderDaysBefore(dto.getReminderDaysBefore());
        holiday.setApplicableFor(dto.getApplicableFor());
        holiday.setShiftBased(dto.isShiftBased());
        holidayRepository.save(holiday);
        return dto;
    }

    // Fetch all holidays between two dates
    public List<HolidayDTO> getAllHolidays(LocalDate start, LocalDate end) {
        List<Holiday> holidays = holidayRepository.findByDateBetween(start, end);
        return holidays.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // Delete holiday by ID
    public void deleteHoliday(Long id) {
        holidayRepository.deleteById(id);
    }

    // Reprocess leave logic
    public void reprocessLeaveForHoliday(Long id) {
        Holiday holiday = holidayRepository.findById(id).orElseThrow(() -> new RuntimeException("Holiday not found"));
        if (holiday.isReprocessLeave()) {
            log.info("Reprocessing leaves for holiday: {}", holiday.getName());
        }
    }

    // Import holidays from CSV
    public String importHolidays(MultipartFile file) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader);

            for (CSVRecord record : records) {
                Holiday holiday = new Holiday();
                holiday.setName(record.get("Name"));
                holiday.setDate(LocalDate.parse(record.get("Date")));
                holiday.setType(HolidayType.valueOf(record.get("Type").toUpperCase()));
                holiday.setDescription(record.get("Description"));
                holiday.setApplicableFor(record.get("ApplicableFor"));
                holiday.setShiftBased(Boolean.parseBoolean(record.get("ShiftBased")));
                holiday.setNotifyViaFeeds(Boolean.parseBoolean(record.get("NotifyViaFeeds")));
                holiday.setReprocessLeave(Boolean.parseBoolean(record.get("ReprocessLeave")));
                holiday.setReminderDaysBefore(Integer.parseInt(record.get("ReminderDaysBefore")));
                holidayRepository.save(holiday);
            }
            return "Holiday import successful.";
        } catch (Exception e) {
            log.error("Import failed", e);
            return "Holiday import failed: " + e.getMessage();
        }
    }

    // Export holidays to CSV
    public ByteArrayInputStream exportHolidays() {
        List<Holiday> holidays = holidayRepository.findAll();

        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             CSVPrinter printer = new CSVPrinter(new PrintWriter(out), CSVFormat.DEFAULT
                     .withHeader("Name", "Date", "Type", "Description", "ApplicableFor", "ShiftBased", "NotifyViaFeeds", "ReprocessLeave", "ReminderDaysBefore"))) {

            for (Holiday holiday : holidays) {
                printer.printRecord(
                        holiday.getName(),
                        holiday.getDate(),
                        holiday.getType(),
                        holiday.getDescription(),
                        holiday.getApplicableFor(),
                        holiday.isShiftBased(),
                        holiday.isNotifyViaFeeds(),
                        holiday.isReprocessLeave(),
                        holiday.getReminderDaysBefore()
                );
            }

            printer.flush();
            return new ByteArrayInputStream(out.toByteArray());

        } catch (IOException e) {
            throw new RuntimeException("Failed to export holidays: " + e.getMessage());
        }
    }

    // Convert entity to DTO
    private HolidayDTO convertToDTO(Holiday holiday) {
        HolidayDTO dto = new HolidayDTO();
        dto.setName(holiday.getName());
        dto.setDate(holiday.getDate());
        dto.setType(holiday.getType());
        dto.setDescription(holiday.getDescription());
        dto.setNotifyViaFeeds(holiday.isNotifyViaFeeds());
        dto.setReprocessLeave(holiday.isReprocessLeave());
        dto.setReminderDaysBefore(holiday.getReminderDaysBefore());
        dto.setApplicableFor(holiday.getApplicableFor());
        dto.setShiftBased(holiday.isShiftBased());
        return dto;
    }
}
