package by.slotify.core.mapper;

import by.slotify.core.dto.RequestDto;
import by.slotify.core.entity.Request;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserMapper.class, TimeSlotMapper.class, ParticipationTypeMapper.class})
public interface RequestMapper {
    @Mapping(source = "user.userId", target = "userId")
    @Mapping(source = "timeSlot.slotId", target = "slotId")
    @Mapping(source = "participationType.participationTypeId", target = "participationTypeId")
    RequestDto toDto(Request request);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "timeSlot", ignore = true)
    @Mapping(target = "participationType", ignore = true)
    Request toEntity(RequestDto requestDto);
}

