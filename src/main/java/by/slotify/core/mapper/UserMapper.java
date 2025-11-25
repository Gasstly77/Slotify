package by.slotify.core.mapper;

import by.slotify.core.dto.UserDto;
import by.slotify.core.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
    User toEntity(UserDto userDto);
}

