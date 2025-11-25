package by.slotify.core.mapper;

import by.slotify.core.dto.LocationDto;
import by.slotify.core.entity.Location;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LocationMapper {
    LocationDto toDto(Location location);
    Location toEntity(LocationDto locationDto);
}

