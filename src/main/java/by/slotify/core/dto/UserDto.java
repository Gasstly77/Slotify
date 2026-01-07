package by.slotify.core.dto;

import by.slotify.core.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @NotBlank(message = "Имя пользователя обязательно для заполнения")
    @Size(min = 3, max = 50, message = "Имя пользователя должно содержать от 3 до 50 символов")
    private String username;

    @NotBlank(message = "Пароль обязателен для заполнения")
    @Size(min = 8, message = "Пароль должен содержать минимум 8 символов")
    private String passwordHash;

    @NotBlank(message = "Email обязателен для заполнения")
    @Email(message = "Email должен быть валидным")
    private String email;

    @NotNull(message = "Роль обязательна для заполнения")
    private User.Role role;
}
