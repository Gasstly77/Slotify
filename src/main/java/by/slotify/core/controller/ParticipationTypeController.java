package by.slotify.core.controller;

import by.slotify.core.dto.ParticipationTypeDto;
import by.slotify.core.service.ParticipationTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/participation-types")
@RequiredArgsConstructor
public class ParticipationTypeController {
    private final ParticipationTypeService participationTypeService;

    @GetMapping
    public ResponseEntity<List<ParticipationTypeDto>> getAllParticipationTypes() {
        List<ParticipationTypeDto> participationTypes = participationTypeService.findAll();
        return ResponseEntity.ok(participationTypes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParticipationTypeDto> getParticipationTypeById(@PathVariable Integer id) {
        return participationTypeService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ParticipationTypeDto> createParticipationType(@RequestBody ParticipationTypeDto participationTypeDto) {
        ParticipationTypeDto created = participationTypeService.create(participationTypeDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParticipationTypeDto> updateParticipationType(@PathVariable Integer id, @RequestBody ParticipationTypeDto participationTypeDto) {
        participationTypeDto.setParticipationTypeId(id);
        ParticipationTypeDto updated = participationTypeService.update(participationTypeDto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParticipationType(@PathVariable Integer id) {
        participationTypeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

