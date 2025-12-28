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

    @NotNull(message = "User ID is required")
    private Integer userId;

    @NotNull(message = "Slot ID is required")
    private Integer slotId;

    @NotNull(message = "Participation type ID is required")
    private Integer participationTypeId;

    @NotNull(message = "Status is required")
    private Request.Status status;

    private LocalDateTime createdAt;
}
