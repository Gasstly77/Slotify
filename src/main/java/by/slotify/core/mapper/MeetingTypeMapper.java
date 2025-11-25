package by.slotify.core.mapper;

import by.slotify.core.dto.MeetingTypeDto;
import by.slotify.core.entity.MeetingType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MeetingTypeMapper {
    MeetingTypeDto toDto(MeetingType meetingType);
    MeetingType toEntity(MeetingTypeDto meetingTypeDto);
}

