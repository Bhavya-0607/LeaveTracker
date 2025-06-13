package com.example.demo.service;

import com.example.demo.Dto.OperationLogDTO;
import com.example.demo.entity.OperationLog;
import com.example.demo.repository.OperationLogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OperationLogServiceTest {

    private OperationLogRepository repository;
    private OperationLogService service;

    @BeforeEach
    public void setUp() {
        repository = mock(OperationLogRepository.class);
        service = new OperationLogService(repository);
    }

    @Test
    public void testLogOperation() {
        OperationLogDTO dto = new OperationLogDTO();
        dto.setModuleName("Leave Tracker");
        dto.setOperation("CREATE");
        dto.setPerformedBy("bhavya");
        dto.setEmployeeId(1001L);
        dto.setDurationInHours(1.5);
        dto.setTimestamp(LocalDateTime.now());

        OperationLog entity = new OperationLog();
        entity.setId(1L);
        entity.setModuleName(dto.getModuleName());
        entity.setOperation(dto.getOperation());
        entity.setPerformedBy(dto.getPerformedBy());
        entity.setEmployeeId(dto.getEmployeeId());
        entity.setDurationInHours(dto.getDurationInHours());
        entity.setTimestamp(dto.getTimestamp());

        when(repository.save(any(OperationLog.class))).thenReturn(entity);

        OperationLogDTO saved = service.logOperation(dto);

        assertNotNull(saved);
        assertEquals("Leave Tracker", saved.getModuleName());
        assertEquals("CREATE", saved.getOperation());
        assertEquals("bhavya", saved.getPerformedBy());
        assertEquals(1.5, saved.getDurationInHours());
        assertEquals(1001L, saved.getEmployeeId());
    }

    @Test
    public void testGetLogsByModule() {
        OperationLog log1 = new OperationLog();
        log1.setId(1L);
        log1.setModuleName("Leave Tracker");
        log1.setOperation("UPDATE");
        log1.setPerformedBy("bhavya");
        log1.setEmployeeId(1001L);
        log1.setDurationInHours(2.0);
        log1.setTimestamp(LocalDateTime.now());

        when(repository.findByModuleNameOrderByTimestampDesc("Leave Tracker"))
                .thenReturn(Arrays.asList(log1));

        List<OperationLogDTO> logs = service.getLogsByModule("Leave Tracker");

        assertEquals(1, logs.size());
        assertEquals("UPDATE", logs.get(0).getOperation());
        assertEquals("bhavya", logs.get(0).getPerformedBy());
        assertEquals(1001L, logs.get(0).getEmployeeId());
    }

    @Test
    public void testGetLogsByUser() {
        OperationLog log1 = new OperationLog();
        log1.setId(1L);
        log1.setModuleName("Leave Tracker");
        log1.setOperation("DELETE");
        log1.setPerformedBy("bhavya");
        log1.setEmployeeId(1001L);
        log1.setDurationInHours(0.75);
        log1.setTimestamp(LocalDateTime.now());

        when(repository.findByPerformedByOrderByTimestampDesc("bhavya"))
                .thenReturn(Arrays.asList(log1));

        List<OperationLogDTO> logs = service.getLogsByUser("bhavya");

        assertEquals(1, logs.size());
        assertEquals("DELETE", logs.get(0).getOperation());
        assertEquals(0.75, logs.get(0).getDurationInHours());
    }
}
