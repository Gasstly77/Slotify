package by.slotify.core.controller;

import by.slotify.core.dto.request.ParticipationTypeRequest;
import by.slotify.core.dto.response.ParticipationTypeResponse;
import by.slotify.core.service.ParticipationTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/participation-types")
@RequiredArgsConstructor
@Tag(name = "Participation Types", description = "API для управления типами участия")
public class ParticipationTypeController {
    private final ParticipationTypeService participationTypeService;

    @GetMapping
    @Operation(summary = "Получить все типы участия")
    public List<ParticipationTypeResponse> getAllParticipationTypes() {
        return participationTypeService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить тип участия по ID")
    public ParticipationTypeResponse getParticipationTypeById(@PathVariable Integer id) {
        return participationTypeService.findById(id)
                .orElseThrow(() -> new RuntimeException("ParticipationType not found with id: " + id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Создать тип участия", description = "Создать тип участия (только для админа)")
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipationTypeResponse createParticipationType(@Valid @RequestBody ParticipationTypeRequest participationTypeRequest) {
        return participationTypeService.create(participationTypeRequest);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Обновить тип участия", description = "Обновить тип участия (только для админа)")
    public ParticipationTypeResponse updateParticipationType(
            @PathVariable Integer id,
            @Valid @RequestBody ParticipationTypeRequest participationTypeRequest) {
        return participationTypeService.update(id, participationTypeRequest);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Удалить тип участия", description = "Удалить тип участия (только для админа)")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteParticipationType(@PathVariable Integer id) {
        participationTypeService.deleteById(id);
    }
}
