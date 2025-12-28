package by.slotify.core.mapper;

import by.slotify.core.dto.request.MeetingTypeRequest;
import by.slotify.core.dto.response.MeetingTypeResponse;
import by.slotify.core.entity.MeetingType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MeetingTypeMapper {
    MeetingTypeResponse toResponse(MeetingType meetingType);
    
    @org.mapstruct.Mapping(target = "meetingTypeId", ignore = true)
    @org.mapstruct.Mapping(target = "meetings", ignore = true)
    MeetingType toEntity(MeetingTypeRequest meetingTypeRequest);
}
