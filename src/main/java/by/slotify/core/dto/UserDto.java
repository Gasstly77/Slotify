package by.slotify.core.dto;

import by.slotify.core.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private Integer userId;
    private String username;
    private String password;
    private String email;
    private User.Role role;
}

