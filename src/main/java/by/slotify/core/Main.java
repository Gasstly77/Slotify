package by.slotify.core;

import by.slotify.core.dto.*;
import by.slotify.core.entity.Meeting;
import by.slotify.core.entity.Notification;
import by.slotify.core.entity.Request;
import by.slotify.core.entity.User;
import by.slotify.core.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
@RequiredArgsConstructor
public class Main {
    private final UserService userService;
    private final MeetingTypeService meetingTypeService;
    private final LocationService locationService;
    private final MeetingService meetingService;
    private final TimeSlotService timeSlotService;
    private final ParticipationTypeService participationTypeService;
    private final RequestService requestService;
    private final NotificationService notificationService;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    public CommandLineRunner initData() {
        return args -> {
            // Create 2 users (2 notes)
            UserDto user1Dto = UserDto.builder()
                    .username("john_mcmaffin")
                    .password("user123")
                    .email("john@example.com")
                    .role(User.Role.USER)
                    .build();
            UserDto user2Dto = UserDto.builder()
                    .username("admin_user")
                    .password("admin456")
                    .email("admin@example.com")
                    .role(User.Role.ADMIN)
                    .build();
            user1Dto = userService.create(user1Dto);
            user2Dto = userService.create(user2Dto);
            System.out.println("Created 2 users");

            // Create meeting type (2 notes)
            MeetingTypeDto meetingType1Dto = MeetingTypeDto.builder()
                    .name("Conference")
                    .description("Large conference event")
                    .build();
            MeetingTypeDto meetingType2Dto = MeetingTypeDto.builder()
                    .name("Workshop")
                    .description("Interactive workshop")
                    .build();
            meetingType1Dto = meetingTypeService.create(meetingType1Dto);
            meetingType2Dto = meetingTypeService.create(meetingType2Dto);
            System.out.println("Created 2 meeting types");

            // Create locations (2 notes)
            LocationDto location1Dto = LocationDto.builder()
                    .name("Prospect")
                    .details("Large conference hall with 500 seats")
                    .build();
            LocationDto location2Dto = LocationDto.builder()
                    .name("Meeting Room")
                    .details("Small training room with 30 seats")
                    .build();
            location1Dto = locationService.create(location1Dto);
            location2Dto = locationService.create(location2Dto);
            System.out.println("Created 2 locations");

            // Create meeting (2 notes)
            MeetingDto meeting1Dto = MeetingDto.builder()
                    .title("Tech Conference 2025")
                    .description("Annual technology conference")
                    .finalTime(LocalDateTime.of(2025, 6, 15, 10, 0))
                    .status(Meeting.Status.PLANNED)
                    .meetingTypeId(meetingType1Dto.getMeetingTypeId())
                    .locationId(location1Dto.getLocationId())
                    .build();
            MeetingDto meeting2Dto = MeetingDto.builder()
                    .title("Java Workshop")
                    .description("Java programming workshop")
                    .finalTime(LocalDateTime.of(2025, 7, 20, 14, 0))
                    .status(Meeting.Status.CONFIRMED)
                    .meetingTypeId(meetingType2Dto.getMeetingTypeId())
                    .locationId(location2Dto.getLocationId())
                    .build();
            meeting1Dto = meetingService.create(meeting1Dto);
            meeting2Dto = meetingService.create(meeting2Dto);
            System.out.println("Created 2 meetings");

            // Create time slot (2 notes)
            TimeSlotDto timeSlot1Dto = TimeSlotDto.builder()
                    .meetingId(meeting1Dto.getMeetingId())
                    .startTime(LocalDateTime.of(2024, 6, 15, 10, 0))
                    .endTime(LocalDateTime.of(2024, 6, 15, 12, 0))
                    .isFinal(false)
                    .build();
            TimeSlotDto timeSlot2Dto = TimeSlotDto.builder()
                    .meetingId(meeting2Dto.getMeetingId())
                    .startTime(LocalDateTime.of(2024, 7, 20, 14, 0))
                    .endTime(LocalDateTime.of(2024, 7, 20, 17, 0))
                    .isFinal(true)
                    .build();
            timeSlot1Dto = timeSlotService.create(timeSlot1Dto);
            timeSlot2Dto = timeSlotService.create(timeSlot2Dto);
            System.out.println("Created 2 time slots");

            // Create participation type (2 notes)
            ParticipationTypeDto participationType1Dto = ParticipationTypeDto.builder()
                    .name("Speaker")
                    .description("Presenting at the event")
                    .build();
            ParticipationTypeDto participationType2Dto = ParticipationTypeDto.builder()
                    .name("Attendee")
                    .description("Attending the event")
                    .build();
            participationType1Dto = participationTypeService.create(participationType1Dto);
            participationType2Dto = participationTypeService.create(participationType2Dto);
            System.out.println("Created 2 participation types");

            // Create meeting request (2 notes)
            RequestDto request1Dto = RequestDto.builder()
                    .userId(user1Dto.getUserId())
                    .slotId(timeSlot1Dto.getSlotId())
                    .participationTypeId(participationType2Dto.getParticipationTypeId())
                    .status(Request.Status.PENDING)
                    .build();
            RequestDto request2Dto = RequestDto.builder()
                    .userId(user2Dto.getUserId())
                    .slotId(timeSlot2Dto.getSlotId())
                    .participationTypeId(participationType1Dto.getParticipationTypeId())
                    .status(Request.Status.ACCEPTED)
                    .build();
            request1Dto = requestService.create(request1Dto);
            request2Dto = requestService.create(request2Dto);
            System.out.println("Created 2 requests");

            // Создание уведомлений (2 notes)
            NotificationDto notification1Dto = NotificationDto.builder()
                    .userId(user1Dto.getUserId())
                    .message("Your request has been submitted")
                    .type(Notification.Type.INFO)
                    .isRead(false)
                    .build();
            NotificationDto notification2Dto = NotificationDto.builder()
                    .userId(user2Dto.getUserId())
                    .message("Your request has been accepted")
                    .type(Notification.Type.CONFIRMATION)
                    .isRead(true)
                    .build();
            notification1Dto = notificationService.create(notification1Dto);
            notification2Dto = notificationService.create(notification2Dto);
            System.out.println("Created 2 notifications");

            System.out.println("\n=== Data initialization completed ===");
            System.out.println("Total users: " + userService.findAll().size());
            System.out.println("Total meeting types: " + meetingTypeService.findAll().size());
            System.out.println("Total locations: " + locationService.findAll().size());
            System.out.println("Total meetings: " + meetingService.findAll().size());
            System.out.println("Total time slots: " + timeSlotService.findAll().size());
            System.out.println("Total participation types: " + participationTypeService.findAll().size());
            System.out.println("Total requests: " + requestService.findAll().size());
            System.out.println("Total notifications: " + notificationService.findAll().size());
        };
    }
}
