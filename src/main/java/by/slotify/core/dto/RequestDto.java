package by.slotify.core.dto;

import by.slotify.core.entity.Request;
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
    private Integer userId;
    private Integer slotId;
    private Integer participationTypeId;
    private Request.Status status;
    private LocalDateTime createdAt;
}

