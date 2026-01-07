package by.slotify.core.mapper;

import by.slotify.core.dto.request.LocationRequest;
import by.slotify.core.dto.response.LocationResponse;
import by.slotify.core.entity.Location;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LocationMapper {
    LocationResponse toResponse(Location location);
    
    @org.mapstruct.Mapping(target = "locationId", ignore = true)
    @org.mapstruct.Mapping(target = "meetings", ignore = true)
    Location toEntity(LocationRequest locationRequest);
}
