package by.slotify.core.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MeetingTypeResponse {
    private Integer meetingTypeId;
    private String name;
    private String description;
}

