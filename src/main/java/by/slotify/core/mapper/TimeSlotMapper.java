package by.slotify.core.mapper;

import by.slotify.core.dto.request.TimeSlotRequest;
import by.slotify.core.dto.response.TimeSlotResponse;
import by.slotify.core.entity.TimeSlot;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {MeetingMapper.class})
public interface TimeSlotMapper {
    @Mapping(source = "meeting.meetingId", target = "meetingId")
    TimeSlotResponse toResponse(TimeSlot timeSlot);

    @Mapping(target = "slotId", ignore = true)
    @Mapping(target = "meeting", ignore = true)
    @Mapping(target = "requests", ignore = true)
    TimeSlot toEntity(TimeSlotRequest timeSlotRequest);
}
