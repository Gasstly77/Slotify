package by.slotify.core.mapper;

import by.slotify.core.dto.ParticipationTypeDto;
import by.slotify.core.entity.ParticipationType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ParticipationTypeMapper {
    ParticipationTypeDto toDto(ParticipationType participationType);
    ParticipationType toEntity(ParticipationTypeDto participationTypeDto);
}

