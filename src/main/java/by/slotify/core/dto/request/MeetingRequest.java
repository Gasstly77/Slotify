package by.slotify.core.dto.request;

import by.slotify.core.entity.Meeting;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MeetingRequest {
    @NotBlank(message = "Title is required")
    @Size(max = 200, message = "Title must not exceed 200 characters")
    private String title;

    @Size(max = 2000, message = "Description must not exceed 2000 characters")
    private String description;

    private LocalDateTime finalTime;

    @NotNull(message = "Status is required")
    private Meeting.Status status;

    @NotNull(message = "Meeting type ID is required")
    private Integer meetingTypeId;

    @NotNull(message = "Location ID is required")
    private Integer locationId;
}

