package by.slotify.core.dto;

import by.slotify.core.entity.Meeting;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MeetingDto {
    private Integer meetingId;

    @NotBlank(message = "Заголовок обязателен для заполнения")
    @Size(max = 200, message = "Заголовок должен содержать не больше 200 символов")
    private String title;

    @Size(max = 2000, message = "Описание должно содержать не больше 2000 символов")
    private String description;

    private LocalDateTime finalTime;

    @NotNull(message = "Статус обязателен для заполнения")
    private Meeting.Status status;

    @NotNull(message = "Ид типа встречи обязательно для заполнения")
    private Integer meetingTypeId;

    @NotNull(message = "Ид локации обязательно для заполнения")
    private Integer locationId;
}
