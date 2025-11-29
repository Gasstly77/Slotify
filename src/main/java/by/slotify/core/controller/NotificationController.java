package by.slotify.core.controller;

import by.slotify.core.dto.NotificationDto;
import by.slotify.core.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
@Tag(name = "Notifications", description = "API для управления уведомлениями")
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping
    @Operation(summary = "Получить все уведомления")
    public ResponseEntity<List<NotificationDto>> getAllNotifications() {
        List<NotificationDto> notifications = notificationService.findAll();
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить уведомление по ID")
    public ResponseEntity<NotificationDto> getNotificationById(@PathVariable Integer id) {
        return notificationService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Создать уведомление")
    public ResponseEntity<NotificationDto> createNotification(@Valid @RequestBody NotificationDto notificationDto) {
        NotificationDto created = notificationService.create(notificationDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить уведомление")
    public ResponseEntity<NotificationDto> updateNotification(@PathVariable Integer id, @Valid @RequestBody NotificationDto notificationDto) {
        notificationDto.setNotificationId(id);
        NotificationDto updated = notificationService.update(notificationDto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить уведомление")
    public ResponseEntity<Void> deleteNotification(@PathVariable Integer id) {
        notificationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
