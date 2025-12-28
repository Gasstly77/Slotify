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

    @NotNull(message = "User ID is required")
    private Integer userId;

    @NotBlank(message = "Message is required")
    @Size(max = 2000, message = "Message must not exceed 2000 characters")
    private String message;

    @NotNull(message = "Type is required")
    private Notification.Type type;

    private LocalDateTime createdAt;

    @NotNull(message = "Is read flag is required")
    private Boolean isRead;
}
