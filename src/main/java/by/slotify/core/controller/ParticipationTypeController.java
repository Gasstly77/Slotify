package by.slotify.core.controller;

import by.slotify.core.dto.ParticipationTypeDto;
import by.slotify.core.service.ParticipationTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<ParticipationTypeDto>> getAllParticipationTypes() {
        List<ParticipationTypeDto> participationTypes = participationTypeService.findAll();
        return ResponseEntity.ok(participationTypes);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить тип участия по ID")
    public ResponseEntity<ParticipationTypeDto> getParticipationTypeById(@PathVariable Integer id) {
        return participationTypeService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Создать тип участия")
    public ResponseEntity<ParticipationTypeDto> createParticipationType(@Valid @RequestBody ParticipationTypeDto participationTypeDto) {
        ParticipationTypeDto created = participationTypeService.create(participationTypeDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить тип участия")
    public ResponseEntity<ParticipationTypeDto> updateParticipationType(@PathVariable Integer id, @Valid @RequestBody ParticipationTypeDto participationTypeDto) {
        participationTypeDto.setParticipationTypeId(id);
        ParticipationTypeDto updated = participationTypeService.update(participationTypeDto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить тип участия")
    public ResponseEntity<Void> deleteParticipationType(@PathVariable Integer id) {
        participationTypeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
