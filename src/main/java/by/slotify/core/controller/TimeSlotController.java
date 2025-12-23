package by.slotify.core.controller;

import by.slotify.core.dto.request.TimeSlotRequest;
import by.slotify.core.dto.response.TimeSlotResponse;
import by.slotify.core.service.TimeSlotService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<TimeSlotResponse>> getAllTimeSlots() {
        List<TimeSlotResponse> timeSlots = timeSlotService.findAll();
        return ResponseEntity.ok(timeSlots);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить временной слот по ID")
    public ResponseEntity<TimeSlotResponse> getTimeSlotById(@PathVariable Integer id) {
        return timeSlotService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Создать временной слот", description = "Создать временной слот для мероприятия (только для админа)")
    public ResponseEntity<TimeSlotResponse> createTimeSlot(@Valid @RequestBody TimeSlotRequest timeSlotRequest) {
        TimeSlotResponse created = timeSlotService.create(timeSlotRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Обновить временной слот", description = "Обновить временной слот (только для админа)")
    public ResponseEntity<TimeSlotResponse> updateTimeSlot(
            @PathVariable Integer id,
            @Valid @RequestBody TimeSlotRequest timeSlotRequest) {
        TimeSlotResponse updated = timeSlotService.update(id, timeSlotRequest);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Удалить временной слот", description = "Удалить временной слот (только для админа)")
    public ResponseEntity<Void> deleteTimeSlot(@PathVariable Integer id) {
        timeSlotService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
