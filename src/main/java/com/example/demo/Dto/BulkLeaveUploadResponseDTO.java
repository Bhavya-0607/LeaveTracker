package com.example.demo.Dto;

import java.util.List;

public class BulkLeaveUploadResponseDTO {

    private int totalRows;
    private int successCount;
    private int failureCount;
    private List<String> errorMessages;

    public BulkLeaveUploadResponseDTO(int totalRows, int successCount, int failureCount, List<String> errorMessages) {
        this.totalRows = totalRows;
        this.successCount = successCount;
        this.failureCount = failureCount;
        this.errorMessages = errorMessages;
    }

    public int getTotalRows() {
        return totalRows;
    }

    public int getSuccessCount() {
        return successCount;
    }

    public int getFailureCount() {
        return failureCount;
    }

    public List<String> getErrorMessages() {
        return errorMessages;
    }
}
