package by.slotify.core.dto.response;

import by.slotify.core.entity.Meeting;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MeetingResponse {
    private Integer meetingId;
    private String title;
    private String description;
    private LocalDateTime finalTime;
    private Meeting.Status status;
    private Integer meetingTypeId;
    private Integer locationId;
}

