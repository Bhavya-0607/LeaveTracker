package com.example.demo.service;

import com.example.demo.Dto.HolidayDTO;
import com.example.demo.entity.Holiday;
import com.example.demo.enums.HolidayType;
import com.example.demo.repository.HolidayRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.mock.web.MockMultipartFile;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HolidayServiceTest {

    @Mock
    private HolidayRepository holidayRepository;

    @InjectMocks
    private HolidayService holidayService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddHoliday() {
        HolidayDTO dto = HolidayDTO.builder()
                .name("New Year")
                .date(LocalDate.of(2025, 1, 1))
                .type(HolidayType.GAZETTED)
                .description("New Year Celebration")
                .notifyViaFeeds(true)
                .reprocessLeave(false)
                .reminderDaysBefore(2)
                .applicableFor("All")
                .shiftBased(false)
                .build();

        Holiday savedHoliday = new Holiday();
        savedHoliday.setName(dto.getName());
        savedHoliday.setDate(dto.getDate());
        savedHoliday.setType(dto.getType());

        when(holidayRepository.save(any(Holiday.class))).thenReturn(savedHoliday);

        HolidayDTO result = holidayService.addHoliday(dto);
        assertEquals("New Year", result.getName());
    }

    @Test
    void testGetAllHolidays() {
        Holiday h1 = new Holiday();
        h1.setName("Republic Day");
        h1.setDate(LocalDate.of(2025, 1, 26));
        h1.setType(HolidayType.GAZETTED);

        when(holidayRepository.findByDateBetween(any(), any())).thenReturn(Collections.singletonList(h1));

        List<HolidayDTO> result = holidayService.getAllHolidays(LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31));
        assertEquals(1, result.size());
        assertEquals("Republic Day", result.get(0).getName());
    }

    @Test
    void testDeleteHoliday() {
        Long holidayId = 1L;
        doNothing().when(holidayRepository).deleteById(holidayId);
        holidayService.deleteHoliday(holidayId);
        verify(holidayRepository, times(1)).deleteById(holidayId);
    }

    @Test
    void testReprocessLeaveForHoliday() {
        Holiday holiday = new Holiday();
        holiday.setId(1L);
        holiday.setName("Sample Holiday");
        holiday.setReprocessLeave(true);

        when(holidayRepository.findById(1L)).thenReturn(Optional.of(holiday));
        assertDoesNotThrow(() -> holidayService.reprocessLeaveForHoliday(1L));
    }

    @Test
    void testImportHolidays() throws Exception {
        String csvContent = "Name,Date,Type,Description,ApplicableFor,ShiftBased,NotifyViaFeeds,ReprocessLeave,ReminderDaysBefore\n" +
                "Independence Day,2025-08-15,GAZETTED,National Holiday,All,false,true,true,2";
        MockMultipartFile file = new MockMultipartFile("file", "holidays.csv", "text/csv", csvContent.getBytes());

        String result = holidayService.importHolidays(file);
        assertEquals("Holiday import successful.", result);
    }

    @Test
    void testExportHolidays() {
        Holiday holiday = new Holiday();
        holiday.setName("Christmas");
        holiday.setDate(LocalDate.of(2025, 12, 25));
        holiday.setType(HolidayType.GAZETTED);
        holiday.setDescription("Holiday Description");
        holiday.setApplicableFor("All");
        holiday.setShiftBased(false);
        holiday.setNotifyViaFeeds(true);
        holiday.setReprocessLeave(false);
        holiday.setReminderDaysBefore(3);

        when(holidayRepository.findAll()).thenReturn(Collections.singletonList(holiday));

        ByteArrayInputStream stream = holidayService.exportHolidays();
        assertNotNull(stream);
    }
}
