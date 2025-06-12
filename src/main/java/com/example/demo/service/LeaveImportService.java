package com.example.demo.service;

import com.example.demo.Dto.BulkLeaveUploadResponseDTO;
import com.example.demo.entity.LeaveImportLog;
import com.example.demo.repository.LeaveImportLogRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Service
public class LeaveImportService {

    private final LeaveImportLogRepository leaveImportLogRepository;

    public LeaveImportService(LeaveImportLogRepository leaveImportLogRepository) {
        this.leaveImportLogRepository = leaveImportLogRepository;
    }

    public BulkLeaveUploadResponseDTO importLeaveData(MultipartFile file, String importedBy) {
        int total = 0, success = 0, failed = 0;
        List<String> errorMessages = new ArrayList<>();

        try {
            String filename = file.getOriginalFilename();

            // Simulated parsing logic
            total = 10;
            success = 8;
            failed = 2;
            errorMessages.add("Row 3: Invalid leave type");
            errorMessages.add("Row 7: Missing employee ID");

            LeaveImportLog log = new LeaveImportLog();
            log.setFileName(filename);
            log.setTotalRows(total);
            log.setSuccessCount(success);
            log.setFailureCount(failed);
            log.setErrorDetails(String.join("\n", errorMessages));
            log.setImportedAt(LocalDateTime.now());
            log.setImportedBy(importedBy);

            leaveImportLogRepository.save(log);

            return new BulkLeaveUploadResponseDTO(total, success, failed, errorMessages);

        } catch (Exception e) {
            throw new RuntimeException("File processing failed", e);
        }
    }
}
