package by.slotify.core.controller;

import by.slotify.core.dto.MeetingDto;
import by.slotify.core.service.MeetingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/meetings")
@RequiredArgsConstructor
@Tag(name = "Meetings", description = "API для управления мероприятиями")
public class MeetingController {
    private final MeetingService meetingService;

    @GetMapping
    @Operation(summary = "Получить все мероприятия")
    public ResponseEntity<List<MeetingDto>> getAllMeetings() {
        List<MeetingDto> meetings = meetingService.findAll();
        return ResponseEntity.ok(meetings);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить мероприятие по ID")
    public ResponseEntity<MeetingDto> getMeetingById(@PathVariable Integer id) {
        return meetingService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Создать мероприятие")
    public ResponseEntity<MeetingDto> createMeeting(@Valid @RequestBody MeetingDto meetingDto) {
        MeetingDto created = meetingService.create(meetingDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить мероприятие")
    public ResponseEntity<MeetingDto> updateMeeting(@PathVariable Integer id, @Valid @RequestBody MeetingDto meetingDto) {
        meetingDto.setMeetingId(id);
        MeetingDto updated = meetingService.update(meetingDto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить мероприятие")
    public ResponseEntity<Void> deleteMeeting(@PathVariable Integer id) {
        meetingService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
