package by.slotify.core.dto;

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
    private String name;
    private String description;
}

