package by.slotify.core.controller;

import by.slotify.core.dto.MeetingTypeDto;
import by.slotify.core.service.MeetingTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<MeetingTypeDto>> getAllMeetingTypes() {
        List<MeetingTypeDto> meetingTypes = meetingTypeService.findAll();
        return ResponseEntity.ok(meetingTypes);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить тип мероприятия по ID")
    public ResponseEntity<MeetingTypeDto> getMeetingTypeById(@PathVariable Integer id) {
        return meetingTypeService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Создать тип мероприятия")
    public ResponseEntity<MeetingTypeDto> createMeetingType(@Valid @RequestBody MeetingTypeDto meetingTypeDto) {
        MeetingTypeDto created = meetingTypeService.create(meetingTypeDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить тип мероприятия")
    public ResponseEntity<MeetingTypeDto> updateMeetingType(@PathVariable Integer id, @Valid @RequestBody MeetingTypeDto meetingTypeDto) {
        meetingTypeDto.setMeetingTypeId(id);
        MeetingTypeDto updated = meetingTypeService.update(meetingTypeDto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить тип мероприятия")
    public ResponseEntity<Void> deleteMeetingType(@PathVariable Integer id) {
        meetingTypeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
