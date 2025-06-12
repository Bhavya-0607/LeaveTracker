package com.example.demo.service;

import com.example.demo.Dto.BulkLeaveUploadResponseDTO;
import com.example.demo.entity.LeaveImportLog;
import com.example.demo.repository.LeaveImportLogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LeaveImportServiceTest {

    private LeaveImportLogRepository mockRepository;
    private LeaveImportService service;

    @BeforeEach
    public void setUp() {
        mockRepository = mock(LeaveImportLogRepository.class);
        service = new LeaveImportService(mockRepository);
    }

    @Test
    public void testImportLeaveData_Successful() throws IOException {
        MultipartFile mockFile = mock(MultipartFile.class);
        when(mockFile.getOriginalFilename()).thenReturn("leaves.xlsx");

        BulkLeaveUploadResponseDTO result = service.importLeaveData(mockFile, "admin@example.com");

        assertEquals(10, result.getTotalRows());
        assertEquals(8, result.getSuccessCount());
        assertEquals(2, result.getFailureCount());
        assertEquals(2, result.getErrorMessages().size());
        assertTrue(result.getErrorMessages().get(0).contains("Invalid leave type"));

        ArgumentCaptor<LeaveImportLog> captor = ArgumentCaptor.forClass(LeaveImportLog.class);
        verify(mockRepository, times(1)).save(captor.capture());

        LeaveImportLog savedLog = captor.getValue();
        assertEquals("leaves.xlsx", savedLog.getFileName());
        assertEquals(10, savedLog.getTotalRows());
        assertEquals(8, savedLog.getSuccessCount());
        assertEquals(2, savedLog.getFailureCount());
        assertEquals("admin@example.com", savedLog.getImportedBy());
        assertNotNull(savedLog.getImportedAt());
        assertTrue(savedLog.getErrorDetails().contains("Invalid leave type"));
    }

    @Test
    public void testImportLeaveData_Exception() {
        MultipartFile mockFile = mock(MultipartFile.class);
        try {
            when(mockFile.getOriginalFilename()).thenReturn("leaves.csv");
        } catch (Exception e) {
            fail("Mock setup failed");
        }

        // Simulate exception from repository save
        doThrow(new RuntimeException("DB Save Failed"))
                .when(mockRepository).save(any(LeaveImportLog.class));

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                service.importLeaveData(mockFile, "admin@example.com"));

        assertEquals("File processing failed", exception.getMessage());
    }
}
