package by.slotify.core.controller;

import by.slotify.core.dto.request.NotificationRequest;
import by.slotify.core.dto.response.NotificationResponse;
import by.slotify.core.service.NotificationService;
import by.slotify.core.util.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
@Tag(name = "Notifications", description = "API для управления уведомлениями")
public class NotificationController {
    private final NotificationService notificationService;
    private final SecurityUtil securityUtil;

    @GetMapping("/my")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Мои уведомления", description = "Получить все уведомления текущего пользователя")
    public ResponseEntity<List<NotificationResponse>> getMyNotifications() {
        Integer userId = securityUtil.getCurrentUserId();
        List<NotificationResponse> notifications = notificationService.findByUserId(userId);
        return ResponseEntity.ok(notifications);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Получить все уведомления", description = "Получить все уведомления (только для админа)")
    public ResponseEntity<List<NotificationResponse>> getAllNotifications() {
        List<NotificationResponse> notifications = notificationService.findAll();
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить уведомление по ID")
    public ResponseEntity<NotificationResponse> getNotificationById(@PathVariable Integer id) {
        return notificationService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Создать уведомление")
    public ResponseEntity<NotificationResponse> createNotification(@Valid @RequestBody NotificationRequest notificationRequest) {
        NotificationResponse created = notificationService.create(notificationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить уведомление")
    public ResponseEntity<NotificationResponse> updateNotification(
            @PathVariable Integer id,
            @Valid @RequestBody NotificationRequest notificationRequest) {
        NotificationResponse updated = notificationService.update(id, notificationRequest);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить уведомление")
    public ResponseEntity<Void> deleteNotification(@PathVariable Integer id) {
        notificationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
