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
        dto.setOperationType("CREATE");
        dto.setPerformedBy("bhavya");
        dto.setTimestamp(LocalDateTime.now());

        OperationLog entity = new OperationLog();
        entity.setId(1L);
        entity.setModuleName(dto.getModuleName());
        entity.setOperationType(dto.getOperationType());
        entity.setPerformedBy(dto.getPerformedBy());
        entity.setTimestamp(dto.getTimestamp());

        when(repository.save(any(OperationLog.class))).thenReturn(entity);

        OperationLogDTO saved = service.logOperation(dto);

        assertNotNull(saved);
        assertEquals("Leave Tracker", saved.getModuleName());
        assertEquals("CREATE", saved.getOperationType());
        assertEquals("bhavya", saved.getPerformedBy());
    }

    @Test
    public void testGetLogsByModule() {
        OperationLog log1 = new OperationLog();
        log1.setId(1L);
        log1.setModuleName("Leave Tracker");
        log1.setOperationType("UPDATE");
        log1.setPerformedBy("bhavya");
        log1.setTimestamp(LocalDateTime.now());

        when(repository.findByModuleNameOrderByTimestampDesc("Leave Tracker"))
                .thenReturn(Arrays.asList(log1));

        List<OperationLogDTO> logs = service.getLogsByModule("Leave Tracker");

        assertEquals(1, logs.size());
        assertEquals("UPDATE", logs.get(0).getOperationType());
    }

    @Test
    public void testGetLogsByUser() {
        OperationLog log1 = new OperationLog();
        log1.setId(1L);
        log1.setModuleName("Leave Tracker");
        log1.setOperationType("DELETE");
        log1.setPerformedBy("bhavya");
        log1.setTimestamp(LocalDateTime.now());

        when(repository.findByPerformedByOrderByTimestampDesc("bhavya"))
                .thenReturn(Arrays.asList(log1));

        List<OperationLogDTO> logs = service.getLogsByUser("bhavya");

        assertEquals(1, logs.size());
        assertEquals("DELETE", logs.get(0).getOperationType());
    }
}
