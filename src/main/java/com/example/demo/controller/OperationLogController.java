package com.example.demo.controller;

import com.example.demo.Dto.OperationLogDTO;
import com.example.demo.service.OperationLogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/operation-logs")
public class OperationLogController {

    private final OperationLogService operationLogService;

    public OperationLogController(OperationLogService operationLogService) {
        this.operationLogService = operationLogService;
    }

    // POST - Log a new operation
    @PostMapping
    public ResponseEntity<OperationLogDTO> logOperation(@RequestBody OperationLogDTO dto) {
        OperationLogDTO savedLog = operationLogService.logOperation(dto);
        return ResponseEntity.ok(savedLog);
    }

    // GET - All logs
    @GetMapping
    public ResponseEntity<List<OperationLogDTO>> getAllLogs() {
        return ResponseEntity.ok(operationLogService.getAllLogs());
    }

    // GET - Logs filtered by module
    @GetMapping("/module/{moduleName}")
    public ResponseEntity<List<OperationLogDTO>> getLogsByModule(@PathVariable String moduleName) {
        return ResponseEntity.ok(operationLogService.getLogsByModule(moduleName));
    }

    // GET - Logs filtered by user
    @GetMapping("/user/{performedBy}")
    public ResponseEntity<List<OperationLogDTO>> getLogsByUser(@PathVariable String performedBy) {
        return ResponseEntity.ok(operationLogService.getLogsByUser(performedBy));
    }
}
