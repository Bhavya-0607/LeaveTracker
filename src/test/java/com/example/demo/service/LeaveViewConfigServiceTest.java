package com.example.demo.service;

import com.example.demo.Dto.LeaveViewConfigDto;
import com.example.demo.entity.LeaveViewConfig;
import com.example.demo.enums.ViewPermission;
import com.example.demo.repository.LeaveViewConfigRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class LeaveViewConfigServiceTest {

    private LeaveViewConfigRepository repository;
    private LeaveViewConfigService service;

    @BeforeEach
    public void setup() {
        repository = mock(LeaveViewConfigRepository.class);
        service = new LeaveViewConfigService(repository);
    }

    @Test
    public void testSaveConfig_NewConfig_ShouldSetCreatedAt() {
        LeaveViewConfig config = new LeaveViewConfig();
        config.setViewName("New View");

        when(repository.save(any())).thenAnswer(i -> i.getArgument(0));

        LeaveViewConfig saved = service.saveConfig(config);
        assertNotNull(saved.getCreatedAt());
    }

    @Test
    public void testSaveConfig_ExistingConfig_ShouldSetModifiedAt() {
        LeaveViewConfig config = new LeaveViewConfig();
        config.setId(1L);
        config.setViewName("Updated View");

        when(repository.save(any())).thenAnswer(i -> i.getArgument(0));

        LeaveViewConfig saved = service.saveConfig(config);
        assertNotNull(saved.getModifiedAt());
    }

    @Test
    public void testGetAllConfigs_ShouldReturnList() {
        when(repository.findAll()).thenReturn(Arrays.asList(new LeaveViewConfig(), new LeaveViewConfig()));
        List<LeaveViewConfig> configs = service.getAllConfigs();
        assertEquals(2, configs.size());
    }

    @Test
    public void testGetConfigById_Exists() {
        LeaveViewConfig config = new LeaveViewConfig();
        config.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(config));

        LeaveViewConfig result = service.getConfigById(1L);
        assertEquals(1L, result.getId().longValue());
    }

    @Test
    public void testGetConfigById_NotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        Exception ex = assertThrows(RuntimeException.class, () -> service.getConfigById(1L));
        assertTrue(ex.getMessage().contains("Leave View Config not found"));
    }

    @Test
    public void testDeleteConfig_Exists() {
        when(repository.existsById(1L)).thenReturn(true);
        service.deleteConfig(1L);
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteConfig_NotFound() {
        when(repository.existsById(1L)).thenReturn(false);

        Exception ex = assertThrows(RuntimeException.class, () -> service.deleteConfig(1L));
        assertTrue(ex.getMessage().contains("Config not found"));
    }

    @Test
    public void testCreateView_ShouldSaveAndReturnDto() {
        LeaveViewConfigDto dto = new LeaveViewConfigDto();
        dto.setViewName("Test View");
        dto.setSelectedColumns(Arrays.asList("name", "email"));
        dto.setViewPermission(ViewPermission.PRIVATE);
        dto.setSharedWithUsers(Arrays.asList(1L, 2L));
        dto.setSharedWithRoles(Collections.singletonList("HR"));
        dto.setSharedWithDepartments(Collections.singletonList("Tech"));

        LeaveViewConfig savedEntity = new LeaveViewConfig();
        savedEntity.setId(1L);
        savedEntity.setViewName(dto.getViewName());
        savedEntity.setSelectedColumns(dto.getSelectedColumns());
        savedEntity.setViewPermission(dto.getViewPermission());
        savedEntity.setSharedWithUsers(dto.getSharedWithUsers());
        savedEntity.setSharedWithRoles(dto.getSharedWithRoles());
        savedEntity.setSharedWithDepartments(dto.getSharedWithDepartments());

        when(repository.save(any())).thenReturn(savedEntity);

        LeaveViewConfigDto result = service.createView(dto, "admin");
        assertEquals("Test View", result.getViewName());
        assertEquals(1L, result.getId());
    }

    @Test
    public void testUpdateView_ShouldUpdateAndReturnDto() {
        LeaveViewConfig existing = new LeaveViewConfig();
        existing.setId(1L);
        existing.setViewName("Old View");

        LeaveViewConfigDto updateDto = new LeaveViewConfigDto();
        updateDto.setViewName("Updated View");
        updateDto.setSelectedColumns(Arrays.asList("name", "email"));
        updateDto.setViewPermission(ViewPermission.PUBLIC);
        updateDto.setSharedWithUsers(Arrays.asList(3L));
        updateDto.setSharedWithRoles(Collections.singletonList("Manager"));
        updateDto.setSharedWithDepartments(Collections.singletonList("Engineering"));
        updateDto.setModifiedBy("admin");

        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        when(repository.save(any())).thenAnswer(i -> i.getArgument(0));

        LeaveViewConfigDto updated = service.updateView(1L, updateDto);
        assertEquals("Updated View", updated.getViewName());
        assertEquals(updateDto.getSharedWithUsers(), updated.getSharedWithUsers());
    }

    @Test
    public void testGetUserViews_ShouldReturnList() {
        LeaveViewConfig config1 = new LeaveViewConfig();
        config1.setId(1L);
        config1.setViewName("My View");
        config1.setSelectedColumns(Arrays.asList("col1", "col2"));
        config1.setViewPermission(ViewPermission.PRIVATE);
        config1.setSharedWithUsers(Arrays.asList(1L));
        config1.setSharedWithRoles(Collections.singletonList("HR"));
        config1.setSharedWithDepartments(Collections.singletonList("Tech"));

        when(repository.findByCreatedBy("admin")).thenReturn(Collections.singletonList(config1));

        List<LeaveViewConfigDto> views = service.getUserViews("admin");
        assertEquals(1, views.size());
        assertEquals("My View", views.get(0).getViewName());
    }
}
