package com.example.demo.controller;

import com.example.demo.Dto.HolidayDTO;
import com.example.demo.service.HolidayService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/holidays")
public class HolidayController {

    private final HolidayService holidayService;

    // Explicit constructor (no Lombok)
    public HolidayController(HolidayService holidayService) {
        this.holidayService = holidayService;
    }

    @PostMapping
    public HolidayDTO addHoliday(@RequestBody HolidayDTO dto) {
        return holidayService.addHoliday(dto);
    }

    @GetMapping
    public List<HolidayDTO> getAllHolidays(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        return holidayService.getAllHolidays(start, end);
    }

    @DeleteMapping("/{id}")
    public void deleteHoliday(@PathVariable Long id) {
        holidayService.deleteHoliday(id);
    }

    @PostMapping("/{id}/reprocess")
    public void reprocessLeave(@PathVariable Long id) {
        holidayService.reprocessLeaveForHoliday(id);
    }

    @PostMapping("/import")
    public ResponseEntity<String> importHolidays(@RequestParam("file") MultipartFile file) {
        String message = holidayService.importHolidays(file);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> exportHolidays() throws IOException {
        ByteArrayInputStream stream = holidayService.exportHolidays();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=holidays.csv");
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(stream.readAllBytes());
    }
}
