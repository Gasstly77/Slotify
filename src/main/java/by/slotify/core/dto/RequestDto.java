package by.slotify.core.dto;

import by.slotify.core.entity.Request;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestDto {
    private Integer requestId;

    @NotNull(message = "Ид пользователя обязательно для заполнения")
    private Integer userId;

    @NotNull(message = "Ид слота обязательно для заполнения")
    private Integer slotId;

    @NotNull(message = "Ид типа участия обязательно для заполнения")
    private Integer participationTypeId;

    @NotNull(message = "Статус обязателен для заполнения")
    private Request.Status status;

    private LocalDateTime createdAt;
}
