package by.slotify.core.mapper;

import by.slotify.core.dto.request.NotificationRequest;
import by.slotify.core.dto.response.NotificationResponse;
import by.slotify.core.entity.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface NotificationMapper {
    @Mapping(source = "user.userId", target = "userId")
    NotificationResponse toResponse(Notification notification);

    @Mapping(target = "notificationId", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Notification toEntity(NotificationRequest notificationRequest);
}
