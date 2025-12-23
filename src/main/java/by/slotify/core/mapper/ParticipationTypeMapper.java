package by.slotify.core.mapper;

import by.slotify.core.dto.request.ParticipationTypeRequest;
import by.slotify.core.dto.response.ParticipationTypeResponse;
import by.slotify.core.entity.ParticipationType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ParticipationTypeMapper {
    ParticipationTypeResponse toResponse(ParticipationType participationType);
    
    @org.mapstruct.Mapping(target = "participationTypeId", ignore = true)
    @org.mapstruct.Mapping(target = "requests", ignore = true)
    ParticipationType toEntity(ParticipationTypeRequest participationTypeRequest);
}
