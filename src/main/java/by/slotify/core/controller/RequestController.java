package by.slotify.core.controller;

import by.slotify.core.dto.request.RequestRequest;
import by.slotify.core.dto.response.RequestResponse;
import by.slotify.core.service.RequestService;
import by.slotify.core.util.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/requests")
@RequiredArgsConstructor
@Tag(name = "Requests", description = "API для управления запросами на участие")
public class RequestController {
    private final RequestService requestService;
    private final SecurityUtil securityUtil;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Получить все запросы", description = "Возвращает страницу запросов с пагинацией (только для админа)")
    public ResponseEntity<Page<RequestResponse>> getAllRequests(
            @PageableDefault(size = 20, sort = "requestId") Pageable pageable) {
        Page<RequestResponse> requests = requestService.findAll(pageable);
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить запрос по ID")
    public ResponseEntity<RequestResponse> getRequestById(@PathVariable Integer id) {
        return requestService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Создать запрос", description = "Создать заявку на участие в мероприятии (только для пользователя)")
    public ResponseEntity<RequestResponse> createRequest(@Valid @RequestBody RequestRequest requestRequest) {
        // Устанавливаем userId текущего пользователя
        Integer currentUserId = securityUtil.getCurrentUserId();
        requestRequest.setUserId(currentUserId);
        
        RequestResponse created = requestService.create(requestRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить запрос")
    public ResponseEntity<RequestResponse> updateRequest(
            @PathVariable Integer id,
            @Valid @RequestBody RequestRequest requestRequest) {
        RequestResponse updated = requestService.update(id, requestRequest);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить запрос")
    public ResponseEntity<Void> deleteRequest(@PathVariable Integer id) {
        requestService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
