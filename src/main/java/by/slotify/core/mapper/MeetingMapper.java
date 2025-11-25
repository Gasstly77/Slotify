package by.slotify.core.mapper;

import by.slotify.core.dto.MeetingDto;
import by.slotify.core.entity.Meeting;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {MeetingTypeMapper.class, LocationMapper.class})
public interface MeetingMapper {
    @Mapping(source = "meetingType.meetingTypeId", target = "meetingTypeId")
    @Mapping(source = "location.locationId", target = "locationId")
    MeetingDto toDto(Meeting meeting);

    @Mapping(target = "meetingType", ignore = true)
    @Mapping(target = "location", ignore = true)
    @Mapping(target = "timeSlots", ignore = true)
    Meeting toEntity(MeetingDto meetingDto);
}

