package by.slotify.core.mapper;

import by.slotify.core.dto.TimeSlotDto;
import by.slotify.core.entity.TimeSlot;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {MeetingMapper.class})
public interface TimeSlotMapper {
    @Mapping(source = "meeting.meetingId", target = "meetingId")
    TimeSlotDto toDto(TimeSlot timeSlot);

    @Mapping(target = "meeting", ignore = true)
    @Mapping(target = "requests", ignore = true)
    TimeSlot toEntity(TimeSlotDto timeSlotDto);
}

