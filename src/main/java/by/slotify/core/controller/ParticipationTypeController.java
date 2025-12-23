package by.slotify.core.controller;

import by.slotify.core.dto.request.ParticipationTypeRequest;
import by.slotify.core.dto.response.ParticipationTypeResponse;
import by.slotify.core.service.ParticipationTypeService;
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
@RequestMapping("/api/participation-types")
@RequiredArgsConstructor
@Tag(name = "Participation Types", description = "API для управления типами участия")
public class ParticipationTypeController {
    private final ParticipationTypeService participationTypeService;

    @GetMapping
    @Operation(summary = "Получить все типы участия")
    public ResponseEntity<List<ParticipationTypeResponse>> getAllParticipationTypes() {
        List<ParticipationTypeResponse> participationTypes = participationTypeService.findAll();
        return ResponseEntity.ok(participationTypes);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить тип участия по ID")
    public ResponseEntity<ParticipationTypeResponse> getParticipationTypeById(@PathVariable Integer id) {
        return participationTypeService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Создать тип участия", description = "Создать тип участия (только для админа)")
    public ResponseEntity<ParticipationTypeResponse> createParticipationType(@Valid @RequestBody ParticipationTypeRequest participationTypeRequest) {
        ParticipationTypeResponse created = participationTypeService.create(participationTypeRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Обновить тип участия", description = "Обновить тип участия (только для админа)")
    public ResponseEntity<ParticipationTypeResponse> updateParticipationType(
            @PathVariable Integer id,
            @Valid @RequestBody ParticipationTypeRequest participationTypeRequest) {
        ParticipationTypeResponse updated = participationTypeService.update(id, participationTypeRequest);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Удалить тип участия", description = "Удалить тип участия (только для админа)")
    public ResponseEntity<Void> deleteParticipationType(@PathVariable Integer id) {
        participationTypeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
