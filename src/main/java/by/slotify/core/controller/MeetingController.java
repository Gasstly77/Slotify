package by.slotify.core.controller;

import by.slotify.core.dto.MeetingDto;
import by.slotify.core.service.MeetingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/meetings")
@RequiredArgsConstructor
public class MeetingController {
    private final MeetingService meetingService;

    @GetMapping
    public ResponseEntity<List<MeetingDto>> getAllMeetings() {
        List<MeetingDto> meetings = meetingService.findAll();
        return ResponseEntity.ok(meetings);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MeetingDto> getMeetingById(@PathVariable Integer id) {
        return meetingService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MeetingDto> createMeeting(@RequestBody MeetingDto meetingDto) {
        MeetingDto created = meetingService.create(meetingDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MeetingDto> updateMeeting(@PathVariable Integer id, @RequestBody MeetingDto meetingDto) {
        meetingDto.setMeetingId(id);
        MeetingDto updated = meetingService.update(meetingDto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMeeting(@PathVariable Integer id) {
        meetingService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

