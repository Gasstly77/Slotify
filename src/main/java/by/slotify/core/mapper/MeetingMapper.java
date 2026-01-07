package by.slotify.core.mapper;

import by.slotify.core.dto.request.MeetingRequest;
import by.slotify.core.dto.response.MeetingResponse;
import by.slotify.core.entity.Meeting;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {MeetingTypeMapper.class, LocationMapper.class})
public interface MeetingMapper {
    @Mapping(source = "meetingType.meetingTypeId", target = "meetingTypeId")
    @Mapping(source = "location.locationId", target = "locationId")
    MeetingResponse toResponse(Meeting meeting);

    @Mapping(target = "meetingId", ignore = true)
    @Mapping(target = "meetingType", ignore = true)
    @Mapping(target = "location", ignore = true)
    @Mapping(target = "timeSlots", ignore = true)
    Meeting toEntity(MeetingRequest meetingRequest);
}
