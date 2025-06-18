package com.example.demo.controller;

import com.example.demo.Dto.BulkLeaveUploadResponseDTO;
import com.example.demo.service.LeaveImportService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/leave/import")
public class LeaveImportController {

    private final LeaveImportService leaveImportService;

    // Constructor injection without Lombok
    public LeaveImportController(LeaveImportService leaveImportService) {
        this.leaveImportService = leaveImportService;
    }

    /**
     * POST endpoint to upload a leave import file (CSV/XLSX).
     *
     * @param file       The uploaded leave import file.
     * @param importedBy Name or identifier of the user importing the data.
     * @return Response containing success/failure info and error messages if any.
     */
    @PostMapping
    public ResponseEntity<BulkLeaveUploadResponseDTO> importLeaveData(
            @RequestParam("file") MultipartFile file,
            @RequestParam("importedBy") String importedBy) {

        BulkLeaveUploadResponseDTO response = leaveImportService.importLeaveData(file, importedBy);
        return ResponseEntity.ok(response);
    }

    /**
     * GET endpoint to download a sample leave import file (XLSX).
     *
     * @return A downloadable Excel template as ByteArrayResource.
     */
    @GetMapping("/sample-template")
    public ResponseEntity<ByteArrayResource> downloadSampleTemplate() {
        byte[] templateContent = leaveImportService.getSampleTemplate();
        ByteArrayResource resource = new ByteArrayResource(templateContent);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=leave_import_template.xlsx");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(templateContent.length)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
