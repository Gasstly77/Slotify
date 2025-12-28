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
    public Page<RequestResponse> getAllRequests(
            @PageableDefault(size = 20, sort = "requestId") Pageable pageable) {
        return requestService.findAll(pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить запрос по ID")
    public RequestResponse getRequestById(@PathVariable Integer id) {
        return requestService.findById(id)
                .orElseThrow(() -> new RuntimeException("Request not found with id: " + id));
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Создать запрос", description = "Создать заявку на участие в мероприятии (только для пользователя)")
    @ResponseStatus(HttpStatus.CREATED)
    public RequestResponse createRequest(@Valid @RequestBody RequestRequest requestRequest) {
        // Устанавливаем userId текущего пользователя
        Integer currentUserId = securityUtil.getCurrentUserId();
        requestRequest.setUserId(currentUserId);
        
        return requestService.create(requestRequest);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить запрос")
    public RequestResponse updateRequest(
            @PathVariable Integer id,
            @Valid @RequestBody RequestRequest requestRequest) {
        return requestService.update(id, requestRequest);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить запрос")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRequest(@PathVariable Integer id) {
        requestService.deleteById(id);
    }
}
