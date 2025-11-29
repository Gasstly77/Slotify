package by.slotify.core.controller;

import by.slotify.core.dto.TimeSlotDto;
import by.slotify.core.service.TimeSlotService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<TimeSlotDto>> getAllTimeSlots() {
        List<TimeSlotDto> timeSlots = timeSlotService.findAll();
        return ResponseEntity.ok(timeSlots);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить временной слот по ID")
    public ResponseEntity<TimeSlotDto> getTimeSlotById(@PathVariable Integer id) {
        return timeSlotService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Создать временной слот")
    public ResponseEntity<TimeSlotDto> createTimeSlot(@Valid @RequestBody TimeSlotDto timeSlotDto) {
        TimeSlotDto created = timeSlotService.create(timeSlotDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить временной слот")
    public ResponseEntity<TimeSlotDto> updateTimeSlot(@PathVariable Integer id, @Valid @RequestBody TimeSlotDto timeSlotDto) {
        timeSlotDto.setSlotId(id);
        TimeSlotDto updated = timeSlotService.update(timeSlotDto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить временной слот")
    public ResponseEntity<Void> deleteTimeSlot(@PathVariable Integer id) {
        timeSlotService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
