package com.example.demo.service;

import com.example.demo.Dto.OperationLogDTO;
import com.example.demo.entity.OperationLog;
import com.example.demo.repository.OperationLogRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OperationLogService {

    private final OperationLogRepository repository;
    public OperationLogService(OperationLogRepository repository) {
        this.repository = repository;
    }

    public OperationLogDTO logOperation(OperationLogDTO dto) {
        OperationLog log = new OperationLog();
        BeanUtils.copyProperties(dto, log);
        return toDTO(repository.save(log));
    }

    public List<OperationLogDTO> getLogsByModule(String moduleName) {
        return repository.findByModuleNameOrderByTimestampDesc(moduleName)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<OperationLogDTO> getLogsByUser(String performedBy) {
        return repository.findByPerformedByOrderByTimestampDesc(performedBy)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<OperationLogDTO> getAllLogs() {
        return repository.findAll()
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    private OperationLogDTO toDTO(OperationLog entity) {
        OperationLogDTO dto = new OperationLogDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }
}
