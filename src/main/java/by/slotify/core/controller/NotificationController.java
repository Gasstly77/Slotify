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
    public List<NotificationResponse> getMyNotifications() {
        Integer userId = securityUtil.getCurrentUserId();
        return notificationService.findByUserId(userId);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Получить все уведомления", description = "Получить все уведомления (только для админа)")
    public List<NotificationResponse> getAllNotifications() {
        return notificationService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить уведомление по ID")
    public NotificationResponse getNotificationById(@PathVariable Integer id) {
        return notificationService.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found with id: " + id));
    }

    @PostMapping
    @Operation(summary = "Создать уведомление")
    @ResponseStatus(HttpStatus.CREATED)
    public NotificationResponse createNotification(@Valid @RequestBody NotificationRequest notificationRequest) {
        return notificationService.create(notificationRequest);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить уведомление")
    public NotificationResponse updateNotification(
            @PathVariable Integer id,
            @Valid @RequestBody NotificationRequest notificationRequest) {
        return notificationService.update(id, notificationRequest);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить уведомление")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteNotification(@PathVariable Integer id) {
        notificationService.deleteById(id);
    }
}
