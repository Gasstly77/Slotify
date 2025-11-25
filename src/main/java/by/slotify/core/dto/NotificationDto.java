package by.slotify.core.dto;

import by.slotify.core.entity.Notification;
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
    private Integer userId;
    private String message;
    private Notification.Type type;
    private LocalDateTime createdAt;
    private Boolean isRead;
}

