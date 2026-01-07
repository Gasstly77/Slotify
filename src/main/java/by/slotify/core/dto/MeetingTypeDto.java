package by.slotify.core.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MeetingTypeDto {
    private Integer meetingTypeId;

    @NotBlank(message = "Имя обязательно для заполнения")
    @Size(max = 100, message = "Имя должно содержать не больше 100 символов")
    private String name;

    @Size(max = 500, message = "Описание должно содержать не больше 500 символов")
    private String description;
}
