package by.slotify.core.mapper;

import by.slotify.core.dto.request.UserRequest;
import by.slotify.core.dto.response.UserResponse;
import by.slotify.core.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse toResponse(User user);
    
    @org.mapstruct.Mapping(target = "userId", ignore = true)
    @org.mapstruct.Mapping(target = "requests", ignore = true)
    @org.mapstruct.Mapping(target = "notifications", ignore = true)
    User toEntity(UserRequest userRequest);
    
    @org.mapstruct.Mapping(target = "passwordHash", ignore = true)
    @org.mapstruct.Mapping(target = "requests", ignore = true)
    @org.mapstruct.Mapping(target = "notifications", ignore = true)
    User toEntity(UserResponse userResponse);
}
