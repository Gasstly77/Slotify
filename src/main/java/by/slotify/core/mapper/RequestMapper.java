package by.slotify.core.mapper;

import by.slotify.core.dto.request.RequestRequest;
import by.slotify.core.dto.response.RequestResponse;
import by.slotify.core.entity.Request;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserMapper.class, TimeSlotMapper.class, ParticipationTypeMapper.class})
public interface RequestMapper {
    @Mapping(source = "user.userId", target = "userId")
    @Mapping(source = "timeSlot.slotId", target = "slotId")
    @Mapping(source = "meeting.meetingId", target = "meetingId")
    @Mapping(source = "participationType.participationTypeId", target = "participationTypeId")
    RequestResponse toResponse(Request request);

    @Mapping(target = "requestId", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "timeSlot", ignore = true)
    @Mapping(target = "meeting", ignore = true)
    @Mapping(target = "participationType", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Request toEntity(RequestRequest requestRequest);
}
