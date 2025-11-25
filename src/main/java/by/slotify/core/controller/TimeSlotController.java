package by.slotify.core.controller;

import by.slotify.core.dto.TimeSlotDto;
import by.slotify.core.service.TimeSlotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/time-slots")
@RequiredArgsConstructor
public class TimeSlotController {
    private final TimeSlotService timeSlotService;

    @GetMapping
    public ResponseEntity<List<TimeSlotDto>> getAllTimeSlots() {
        List<TimeSlotDto> timeSlots = timeSlotService.findAll();
        return ResponseEntity.ok(timeSlots);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TimeSlotDto> getTimeSlotById(@PathVariable Integer id) {
        return timeSlotService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TimeSlotDto> createTimeSlot(@RequestBody TimeSlotDto timeSlotDto) {
        TimeSlotDto created = timeSlotService.create(timeSlotDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TimeSlotDto> updateTimeSlot(@PathVariable Integer id, @RequestBody TimeSlotDto timeSlotDto) {
        timeSlotDto.setSlotId(id);
        TimeSlotDto updated = timeSlotService.update(timeSlotDto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTimeSlot(@PathVariable Integer id) {
        timeSlotService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

