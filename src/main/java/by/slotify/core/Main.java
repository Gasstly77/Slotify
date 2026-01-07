package by.slotify.core;

import by.slotify.core.dto.request.*;
import by.slotify.core.dto.response.*;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;

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
    private final PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

///*    @Bean
//    public CommandLineRunner initData() {
//        return args -> {
//            // Create 2 users (2 notes)
//            UserRequest user1Request = UserRequest.builder()
//                    .username("john_mcmaffin")
//                    .passwordHash(passwordEncoder.encode("user123"))
//                    .email("john@example.com")
//                    .role(User.Role.USER)
//                    .build();
//            UserRequest user2Request = UserRequest.builder()
//                    .username("admin_user")
//                    .passwordHash(passwordEncoder.encode("admin456"))
//                    .email("admin@example.com")
//                    .role(User.Role.ADMIN)
//                    .build();
//            UserResponse user1Response = userService.create(user1Request);
//            UserResponse user2Response = userService.create(user2Request);
//            System.out.println("Created 2 users");
//
//            // Create meeting type (2 notes)
//            MeetingTypeRequest meetingType1Request = MeetingTypeRequest.builder()
//                    .name("Conference")
//                    .description("Large conference event")
//                    .build();
//            MeetingTypeRequest meetingType2Request = MeetingTypeRequest.builder()
//                    .name("Workshop")
//                    .description("Interactive workshop")
//                    .build();
//            MeetingTypeResponse meetingType1Response = meetingTypeService.create(meetingType1Request);
//            MeetingTypeResponse meetingType2Response = meetingTypeService.create(meetingType2Request);
//            System.out.println("Created 2 meeting types");
//
//            // Create locations (2 notes)
//            LocationRequest location1Request = LocationRequest.builder()
//                    .name("Prospect")
//                    .details("Large conference hall with 500 seats")
//                    .build();
//            LocationRequest location2Request = LocationRequest.builder()
//                    .name("Meeting Room")
//                    .details("Small training room with 30 seats")
//                    .build();
//            LocationResponse location1Response = locationService.create(location1Request);
//            LocationResponse location2Response = locationService.create(location2Request);
//            System.out.println("Created 2 locations");
//
//            // Create meeting (2 notes)
//            MeetingRequest meeting1Request = MeetingRequest.builder()
//                    .title("Tech Conference 2025")
//                    .description("Annual technology conference")
//                    .finalTime(LocalDateTime.of(2025, 6, 15, 10, 0))
//                    .status(Meeting.Status.PLANNED)
//                    .meetingTypeId(meetingType1Response.getMeetingTypeId())
//                    .locationId(location1Response.getLocationId())
//                    .build();
//            MeetingRequest meeting2Request = MeetingRequest.builder()
//                    .title("Java Workshop")
//                    .description("Java programming workshop")
//                    .finalTime(LocalDateTime.of(2025, 7, 20, 14, 0))
//                    .status(Meeting.Status.CONFIRMED)
//                    .meetingTypeId(meetingType2Response.getMeetingTypeId())
//                    .locationId(location2Response.getLocationId())
//                    .build();
//            MeetingResponse meeting1Response = meetingService.create(meeting1Request);
//            MeetingResponse meeting2Response = meetingService.create(meeting2Request);
//            System.out.println("Created 2 meetings");
//
//            // Create time slot (2 notes)
//            TimeSlotRequest timeSlot1Request = TimeSlotRequest.builder()
//                    .meetingId(meeting1Response.getMeetingId())
//                    .startTime(LocalDateTime.of(2024, 6, 15, 10, 0))
//                    .endTime(LocalDateTime.of(2024, 6, 15, 12, 0))
//                    .isFinal(false)
//                    .build();
//            TimeSlotRequest timeSlot2Request = TimeSlotRequest.builder()
//                    .meetingId(meeting2Response.getMeetingId())
//                    .startTime(LocalDateTime.of(2024, 7, 20, 14, 0))
//                    .endTime(LocalDateTime.of(2024, 7, 20, 17, 0))
//                    .isFinal(true)
//                    .build();
//            TimeSlotResponse timeSlot1Response = timeSlotService.create(timeSlot1Request);
//            TimeSlotResponse timeSlot2Response = timeSlotService.create(timeSlot2Request);
//            System.out.println("Created 2 time slots");
//
//            // Create participation type (2 notes)
//            ParticipationTypeRequest participationType1Request = ParticipationTypeRequest.builder()
//                    .name("Speaker")
//                    .description("Presenting at the event")
//                    .build();
//            ParticipationTypeRequest participationType2Request = ParticipationTypeRequest.builder()
//                    .name("Attendee")
//                    .description("Attending the event")
//                    .build();
//            ParticipationTypeResponse participationType1Response = participationTypeService.create(participationType1Request);
//            ParticipationTypeResponse participationType2Response = participationTypeService.create(participationType2Request);
//            System.out.println("Created 2 participation types");
//
//            // Create meeting request (2 notes)
//            RequestRequest request1Request = RequestRequest.builder()
//                    .userId(user1Response.getUserId())
//                    .slotId(timeSlot1Response.getSlotId())
//                    .participationTypeId(participationType2Response.getParticipationTypeId())
//                    .status(Request.Status.PENDING)
//                    .build();
//            RequestRequest request2Request = RequestRequest.builder()
//                    .userId(user2Response.getUserId())
//                    .slotId(timeSlot2Response.getSlotId())
//                    .participationTypeId(participationType1Response.getParticipationTypeId())
//                    .status(Request.Status.ACCEPTED)
//                    .build();
//            requestService.create(request1Request);
//            requestService.create(request2Request);
//            System.out.println("Created 2 requests");
//
//            // Создание уведомлений (2 notes)
//            NotificationRequest notification1Request = NotificationRequest.builder()
//                    .userId(user1Response.getUserId())
//                    .message("Your request has been submitted")
//                    .type(Notification.Type.INFO)
//                    .isRead(false)
//                    .build();
//            NotificationRequest notification2Request = NotificationRequest.builder()
//                    .userId(user2Response.getUserId())
//                    .message("Your request has been accepted")
//                    .type(Notification.Type.CONFIRMATION)
//                    .isRead(true)
//                    .build();
//            notificationService.create(notification1Request);
//            notificationService.create(notification2Request);
//            System.out.println("Created 2 notifications");
//
//            System.out.println("\n=== Data initialization completed ===");
//            System.out.println("Total users: " + userService.findAll(PageRequest.of(0, 10)).getTotalElements());
//            System.out.println("Total meeting types: " + meetingTypeService.findAll().size());
//            System.out.println("Total locations: " + locationService.findAll().size());
//            System.out.println("Total meetings: " + meetingService.findAll(PageRequest.of(0, 10)).getTotalElements());
//            System.out.println("Total time slots: " + timeSlotService.findAll().size());
//            System.out.println("Total participation types: " + participationTypeService.findAll().size());
//            System.out.println("Total requests: " + requestService.findAll(PageRequest.of(0, 10)).getTotalElements());
//            System.out.println("Total notifications: " + notificationService.findAll().size());
//        };
//    }*/
}
