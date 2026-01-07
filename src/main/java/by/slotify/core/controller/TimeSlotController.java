package by.slotify.core.controller;

import by.slotify.core.dto.request.TimeSlotRequest;
import by.slotify.core.dto.response.TimeSlotResponse;
import by.slotify.core.service.TimeSlotService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/time-slots")
@RequiredArgsConstructor
@Tag(name = "Time Slots", description = "API для управления временными слотами")
public class TimeSlotController {
    private final TimeSlotService timeSlotService;

    @GetMapping
    @Operation(summary = "Получить все временные слоты")
    public List<TimeSlotResponse> getAllTimeSlots() {
        return timeSlotService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить временной слот по ID")
    public TimeSlotResponse getTimeSlotById(@PathVariable Integer id) {
        return timeSlotService.findById(id)
                .orElseThrow(() -> new RuntimeException("TimeSlot not found with id: " + id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Создать временной слот", description = "Создать временной слот для мероприятия (только для админа)")
    @ResponseStatus(HttpStatus.CREATED)
    public TimeSlotResponse createTimeSlot(@Valid @RequestBody TimeSlotRequest timeSlotRequest) {
        return timeSlotService.create(timeSlotRequest);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Обновить временной слот", description = "Обновить временной слот (только для админа)")
    public TimeSlotResponse updateTimeSlot(
            @PathVariable Integer id,
            @Valid @RequestBody TimeSlotRequest timeSlotRequest) {
        return timeSlotService.update(id, timeSlotRequest);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Удалить временной слот", description = "Удалить временной слот (только для админа)")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTimeSlot(@PathVariable Integer id) {
        timeSlotService.deleteById(id);
    }
}
