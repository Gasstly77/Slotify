package by.slotify.core.controller;

import by.slotify.core.dto.request.MeetingTypeRequest;
import by.slotify.core.dto.response.MeetingTypeResponse;
import by.slotify.core.service.MeetingTypeService;
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
@RequestMapping("/api/meeting-types")
@RequiredArgsConstructor
@Tag(name = "Meeting Types", description = "API для управления типами мероприятий")
public class MeetingTypeController {
    private final MeetingTypeService meetingTypeService;

    @GetMapping
    @Operation(summary = "Получить все типы мероприятий")
    public ResponseEntity<List<MeetingTypeResponse>> getAllMeetingTypes() {
        List<MeetingTypeResponse> meetingTypes = meetingTypeService.findAll();
        return ResponseEntity.ok(meetingTypes);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить тип мероприятия по ID")
    public ResponseEntity<MeetingTypeResponse> getMeetingTypeById(@PathVariable Integer id) {
        return meetingTypeService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Создать тип мероприятия", description = "Создать тип мероприятия (только для админа)")
    public ResponseEntity<MeetingTypeResponse> createMeetingType(@Valid @RequestBody MeetingTypeRequest meetingTypeRequest) {
        MeetingTypeResponse created = meetingTypeService.create(meetingTypeRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Обновить тип мероприятия", description = "Обновить тип мероприятия (только для админа)")
    public ResponseEntity<MeetingTypeResponse> updateMeetingType(
            @PathVariable Integer id,
            @Valid @RequestBody MeetingTypeRequest meetingTypeRequest) {
        MeetingTypeResponse updated = meetingTypeService.update(id, meetingTypeRequest);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Удалить тип мероприятия", description = "Удалить тип мероприятия (только для админа)")
    public ResponseEntity<Void> deleteMeetingType(@PathVariable Integer id) {
        meetingTypeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
