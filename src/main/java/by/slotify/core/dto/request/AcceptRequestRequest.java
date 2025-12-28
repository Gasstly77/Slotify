package by.slotify.core.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AcceptRequestRequest {
    private Integer requestId;
    private Boolean accept; // true - принять, false - отклонить
}

