package by.slotify.core.controller;

import by.slotify.core.dto.response.MeetingResponse;
import by.slotify.core.dto.response.RequestResponse;
import by.slotify.core.service.MeetingService;
import by.slotify.core.service.RequestService;
import by.slotify.core.util.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Tag(name = "User Operations", description = "API для операций пользователя")
public class UserMeetingController {
    private final RequestService requestService;
    private final MeetingService meetingService;
    private final SecurityUtil securityUtil;

    @GetMapping("/my-requests")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Мои заявки", description = "Получить все заявки текущего пользователя")
    public ResponseEntity<Page<RequestResponse>> getMyRequests(
            @PageableDefault(size = 20, sort = "requestId") Pageable pageable) {
        Integer userId = securityUtil.getCurrentUserId();
        Page<RequestResponse> requests = requestService.findByUserId(userId, pageable);
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/my-accepted-meetings")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Мои назначенные встречи", description = "Получить все принятые заявки текущего пользователя")
    public ResponseEntity<List<RequestResponse>> getMyAcceptedMeetings() {
        Integer userId = securityUtil.getCurrentUserId();
        List<RequestResponse> requests = requestService.findMyAcceptedRequests(userId);
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/active-meetings")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Активные мероприятия", description = "Получить все активные мероприятия доступные для участия")
    public ResponseEntity<Page<MeetingResponse>> getActiveMeetings(
            @PageableDefault(size = 20, sort = "meetingId") Pageable pageable) {
        Page<MeetingResponse> meetings = meetingService.findActiveMeetings(pageable);
        return ResponseEntity.ok(meetings);
    }
}

