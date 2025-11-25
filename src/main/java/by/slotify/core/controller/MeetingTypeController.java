package by.slotify.core.controller;

import by.slotify.core.dto.MeetingTypeDto;
import by.slotify.core.service.MeetingTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/meeting-types")
@RequiredArgsConstructor
public class MeetingTypeController {
    private final MeetingTypeService meetingTypeService;

    @GetMapping
    public ResponseEntity<List<MeetingTypeDto>> getAllMeetingTypes() {
        List<MeetingTypeDto> meetingTypes = meetingTypeService.findAll();
        return ResponseEntity.ok(meetingTypes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MeetingTypeDto> getMeetingTypeById(@PathVariable Integer id) {
        return meetingTypeService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MeetingTypeDto> createMeetingType(@RequestBody MeetingTypeDto meetingTypeDto) {
        MeetingTypeDto created = meetingTypeService.create(meetingTypeDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MeetingTypeDto> updateMeetingType(@PathVariable Integer id, @RequestBody MeetingTypeDto meetingTypeDto) {
        meetingTypeDto.setMeetingTypeId(id);
        MeetingTypeDto updated = meetingTypeService.update(meetingTypeDto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMeetingType(@PathVariable Integer id) {
        meetingTypeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

