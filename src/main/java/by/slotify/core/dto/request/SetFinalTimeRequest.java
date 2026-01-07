package by.slotify.core.dto.request;

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
public class SetFinalTimeRequest {
    @NotNull(message = "Final time is required")
    private LocalDateTime finalTime;
    
    @NotNull(message = "Time slot ID is required")
    private Integer timeSlotId;
}

