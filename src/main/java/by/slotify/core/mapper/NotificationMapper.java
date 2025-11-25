package by.slotify.core.mapper;

import by.slotify.core.dto.NotificationDto;
import by.slotify.core.entity.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface NotificationMapper {
    @Mapping(source = "user.userId", target = "userId")
    NotificationDto toDto(Notification notification);

    @Mapping(target = "user", ignore = true)
    Notification toEntity(NotificationDto notificationDto);
}

