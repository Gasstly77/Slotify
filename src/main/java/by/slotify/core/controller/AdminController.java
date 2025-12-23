package by.slotify.core.controller;

import by.slotify.core.dto.request.AcceptRequestRequest;
import by.slotify.core.dto.request.SetFinalTimeRequest;
import by.slotify.core.dto.response.RequestResponse;
import by.slotify.core.dto.response.TimeSlotResponse;
import by.slotify.core.service.RequestService;
import by.slotify.core.service.TimeSlotService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Tag(name = "Admin Operations", description = "API для операций администратора")
public class AdminController {
    private final RequestService requestService;
    private final TimeSlotService timeSlotService;

    @GetMapping("/meetings/{meetingId}/requests")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Заявки на мероприятие", description = "Получить все заявки на указанное мероприятие")
    public ResponseEntity<Page<RequestResponse>> getMeetingRequests(
            @PathVariable Integer meetingId,
            @PageableDefault(size = 20, sort = "requestId") Pageable pageable) {
        Page<RequestResponse> requests = requestService.findByMeetingId(meetingId, pageable);
        return ResponseEntity.ok(requests);
    }

    @PostMapping("/requests/accept-or-reject")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Принять/отклонить заявку", description = "Принять или отклонить заявку на участие")
    public ResponseEntity<RequestResponse> acceptOrRejectRequest(
            @Valid @RequestBody AcceptRequestRequest acceptRequest) {
        RequestResponse response = requestService.acceptOrRejectRequest(acceptRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/time-slots/set-final-time")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Назначить итоговое время", description = "Установить финальное время для временного слота")
    public ResponseEntity<TimeSlotResponse> setFinalTime(
            @Valid @RequestBody SetFinalTimeRequest setFinalTimeRequest) {
        TimeSlotResponse response = timeSlotService.setFinalTime(setFinalTimeRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/meetings/{meetingId}/time-slots")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Временные слоты мероприятия", description = "Получить все временные слоты для мероприятия")
    public ResponseEntity<List<TimeSlotResponse>> getMeetingTimeSlots(@PathVariable Integer meetingId) {
        List<TimeSlotResponse> timeSlots = timeSlotService.findByMeetingId(meetingId);
        return ResponseEntity.ok(timeSlots);
    }
}

