package by.slotify.core.dto;

import by.slotify.core.entity.Notification;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationDto {
    private Integer notificationId;

    @NotNull(message = "Ид пользователя обязательно для заполнения")
    private Integer userId;

    @NotBlank(message = "Сообщение обязательно для заполнения")
    @Size(max = 2000, message = "Сообщение должно содержать не больше 2000 символов")
    private String message;

    @NotNull(message = "Тип обязателен для заполнения")
    private Notification.Type type;

    private LocalDateTime createdAt;

    @NotNull(message = "Флаг статуса прочтения обязателен для заполнения")
    private Boolean isRead;
}
