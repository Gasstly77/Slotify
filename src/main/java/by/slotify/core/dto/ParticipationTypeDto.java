package by.slotify.core.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParticipationTypeDto {
    private Integer participationTypeId;
    private String name;
    private String description;
}

