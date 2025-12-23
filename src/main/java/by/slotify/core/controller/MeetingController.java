package by.slotify.core.controller;

import by.slotify.core.dto.request.MeetingRequest;
import by.slotify.core.dto.response.MeetingResponse;
import by.slotify.core.service.MeetingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/meetings")
@RequiredArgsConstructor
@Tag(name = "Meetings", description = "API для управления мероприятиями")
public class MeetingController {
    private final MeetingService meetingService;

    @GetMapping
    @Operation(summary = "Получить все мероприятия", description = "Возвращает страницу мероприятий с пагинацией")
    public ResponseEntity<Page<MeetingResponse>> getAllMeetings(
            @PageableDefault(size = 20, sort = "meetingId") Pageable pageable) {
        Page<MeetingResponse> meetings = meetingService.findAll(pageable);
        return ResponseEntity.ok(meetings);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить мероприятие по ID")
    public ResponseEntity<MeetingResponse> getMeetingById(@PathVariable Integer id) {
        return meetingService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Создать мероприятие", description = "Создать новое мероприятие (только для админа)")
    public ResponseEntity<MeetingResponse> createMeeting(@Valid @RequestBody MeetingRequest meetingRequest) {
        MeetingResponse created = meetingService.create(meetingRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Обновить мероприятие", description = "Обновить мероприятие (только для админа)")
    public ResponseEntity<MeetingResponse> updateMeeting(
            @PathVariable Integer id,
            @Valid @RequestBody MeetingRequest meetingRequest) {
        MeetingResponse updated = meetingService.update(id, meetingRequest);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Удалить мероприятие", description = "Удалить мероприятие (только для админа)")
    public ResponseEntity<Void> deleteMeeting(@PathVariable Integer id) {
        meetingService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
