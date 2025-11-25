package by.slotify.core.dto;

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
    private Integer meetingId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Boolean isFinal;
}

