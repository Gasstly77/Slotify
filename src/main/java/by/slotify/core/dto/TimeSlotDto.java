package by.slotify.core.dto;

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
public class TimeSlotDto {
    private Integer slotId;

    @NotNull(message = "Ид мероприятия обязательно для заполнения")
    private Integer meetingId;

    @NotNull(message = "Время начала обязательно для заполнения")
    private LocalDateTime startTime;

    @NotNull(message = "Время завершения обязательно для заполнения")
    private LocalDateTime endTime;

    @NotNull(message = "Окончательное обязательно для заполнения")
    private Boolean isFinal;
}
