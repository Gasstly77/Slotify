package by.slotify.core.controller;

import by.slotify.core.dto.request.MeetingTypeRequest;
import by.slotify.core.dto.response.MeetingTypeResponse;
import by.slotify.core.service.MeetingTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public List<MeetingTypeResponse> getAllMeetingTypes() {
        return meetingTypeService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить тип мероприятия по ID")
    public MeetingTypeResponse getMeetingTypeById(@PathVariable Integer id) {
        return meetingTypeService.findById(id)
                .orElseThrow(() -> new RuntimeException("MeetingType not found with id: " + id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Создать тип мероприятия", description = "Создать тип мероприятия (только для админа)")
    @ResponseStatus(HttpStatus.CREATED)
    public MeetingTypeResponse createMeetingType(@Valid @RequestBody MeetingTypeRequest meetingTypeRequest) {
        return meetingTypeService.create(meetingTypeRequest);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Обновить тип мероприятия", description = "Обновить тип мероприятия (только для админа)")
    public MeetingTypeResponse updateMeetingType(
            @PathVariable Integer id,
            @Valid @RequestBody MeetingTypeRequest meetingTypeRequest) {
        return meetingTypeService.update(id, meetingTypeRequest);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Удалить тип мероприятия", description = "Удалить тип мероприятия (только для админа)")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMeetingType(@PathVariable Integer id) {
        meetingTypeService.deleteById(id);
    }
}
