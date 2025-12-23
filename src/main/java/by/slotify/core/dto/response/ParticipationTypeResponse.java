package by.slotify.core.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParticipationTypeResponse {
    private Integer participationTypeId;
    private String name;
    private String description;
}

