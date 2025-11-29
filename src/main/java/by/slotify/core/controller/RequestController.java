package by.slotify.core.controller;

import by.slotify.core.dto.RequestDto;
import by.slotify.core.service.RequestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/requests")
@RequiredArgsConstructor
@Tag(name = "Requests", description = "API для управления запросами на участие")
public class RequestController {
    private final RequestService requestService;

    @GetMapping
    @Operation(summary = "Получить все запросы")
    public ResponseEntity<List<RequestDto>> getAllRequests() {
        List<RequestDto> requests = requestService.findAll();
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить запрос по ID")
    public ResponseEntity<RequestDto> getRequestById(@PathVariable Integer id) {
        return requestService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Создать запрос")
    public ResponseEntity<RequestDto> createRequest(@Valid @RequestBody RequestDto requestDto) {
        RequestDto created = requestService.create(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить запрос")
    public ResponseEntity<RequestDto> updateRequest(@PathVariable Integer id, @Valid @RequestBody RequestDto requestDto) {
        requestDto.setRequestId(id);
        RequestDto updated = requestService.update(requestDto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить запрос")
    public ResponseEntity<Void> deleteRequest(@PathVariable Integer id) {
        requestService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
