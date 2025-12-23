package by.slotify.core.service;

import by.slotify.core.dto.request.SetFinalTimeRequest;
import by.slotify.core.dto.request.TimeSlotRequest;
import by.slotify.core.dto.response.TimeSlotResponse;
import by.slotify.core.entity.Meeting;
import by.slotify.core.entity.Notification;
import by.slotify.core.entity.Request;
import by.slotify.core.entity.TimeSlot;
import by.slotify.core.entity.User;
import by.slotify.core.mapper.TimeSlotMapper;
import by.slotify.core.repository.MeetingRepository;
import by.slotify.core.repository.NotificationRepository;
import by.slotify.core.repository.RequestRepository;
import by.slotify.core.repository.TimeSlotRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TimeSlotService {
    private final TimeSlotRepository timeSlotRepository;
    private final MeetingRepository meetingRepository;
    private final NotificationRepository notificationRepository;
    private final RequestRepository requestRepository;
    private final TimeSlotMapper timeSlotMapper;

    @Transactional
    public TimeSlotResponse create(TimeSlotRequest timeSlotRequest) {
        TimeSlot timeSlot = timeSlotMapper.toEntity(timeSlotRequest);
        
        Meeting meeting = meetingRepository.findById(timeSlotRequest.getMeetingId())
                .orElseThrow(() -> new RuntimeException("Meeting not found with id: " + timeSlotRequest.getMeetingId()));
        
        timeSlot.setMeeting(meeting);
        
        TimeSlot saved = timeSlotRepository.save(timeSlot);
        return timeSlotMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public Optional<TimeSlotResponse> findById(Integer id) {
        return timeSlotRepository.findById(id)
                .map(timeSlotMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public List<TimeSlotResponse> findAll() {
        return timeSlotRepository.findAll().stream()
                .map(timeSlotMapper::toResponse)
                .toList();
    }

    @Transactional
    public TimeSlotResponse update(Integer id, TimeSlotRequest timeSlotRequest) {
        TimeSlot timeSlot = timeSlotRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TimeSlot not found with id: " + id));
        
        timeSlot.setStartTime(timeSlotRequest.getStartTime());
        timeSlot.setEndTime(timeSlotRequest.getEndTime());
        timeSlot.setIsFinal(timeSlotRequest.getIsFinal());
        
        if (timeSlotRequest.getMeetingId() != null) {
            Meeting meeting = meetingRepository.findById(timeSlotRequest.getMeetingId())
                    .orElseThrow(() -> new RuntimeException("Meeting not found with id: " + timeSlotRequest.getMeetingId()));
            timeSlot.setMeeting(meeting);
        }
        
        TimeSlot saved = timeSlotRepository.save(timeSlot);
        return timeSlotMapper.toResponse(saved);
    }

    @Transactional
    public void deleteById(Integer id) {
        TimeSlot timeSlot = timeSlotRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TimeSlot not found with id: " + id));
        
        Meeting meeting = timeSlot.getMeeting();
        
        // Отправляем уведомления всем пользователям с заявками на этот слот
        List<Request> requests = requestRepository.findByTimeSlot(timeSlot);

        for (Request request : requests) {
            User user = request.getUser();
            Notification notification = Notification.builder()
                    .user(user)
                    .message("Time slot for meeting '" + meeting.getTitle() + "' has been cancelled. Your request has been rejected.")
                    .type(Notification.Type.WARNING)
                    .isRead(false)
                    .build();
            notificationRepository.save(notification);
            
            // Отклоняем заявку
            request.setStatus(Request.Status.REJECTED);
            requestRepository.save(request);
        }
        
        timeSlotRepository.deleteById(id);
        log.info("Time slot {} deleted, notifications sent to {} users", id, requests.size());
    }

    @Transactional(readOnly = true)
    public List<TimeSlotResponse> findByMeetingId(Integer meetingId) {
        return timeSlotRepository.findByMeeting_MeetingId(meetingId).stream()
                .map(timeSlotMapper::toResponse)
                .toList();
    }

    @Transactional
    public TimeSlotResponse setFinalTime(SetFinalTimeRequest setFinalTimeRequest) {
        TimeSlot timeSlot = timeSlotRepository.findById(setFinalTimeRequest.getTimeSlotId())
                .orElseThrow(() -> new RuntimeException("TimeSlot not found with id: " + setFinalTimeRequest.getTimeSlotId()));

        timeSlot.setIsFinal(true);
        timeSlot.setStartTime(setFinalTimeRequest.getFinalTime());
        timeSlot.setEndTime(setFinalTimeRequest.getFinalTime().plusHours(1)); // Предполагаем 1 час

        Meeting meeting = timeSlot.getMeeting();
        meeting.setFinalTime(setFinalTimeRequest.getFinalTime());
        meeting.setStatus(Meeting.Status.CONFIRMED);
        meetingRepository.save(meeting);

        TimeSlot saved = timeSlotRepository.save(timeSlot);

        // Отправляем уведомления всем пользователям с принятыми заявками на этот слот
        List<Request> acceptedRequests = requestRepository.findByTimeSlotAndStatus(timeSlot, Request.Status.ACCEPTED);

        for (Request request : acceptedRequests) {
            User user = request.getUser();
            Notification notification = Notification.builder()
                    .user(user)
                    .message("Final time has been set for your meeting: " + meeting.getTitle() + 
                            " at " + setFinalTimeRequest.getFinalTime())
                    .type(Notification.Type.CONFIRMATION)
                    .isRead(false)
                    .build();
            notificationRepository.save(notification);
        }

        log.info("Final time set for time slot {}: {}", timeSlot.getSlotId(), setFinalTimeRequest.getFinalTime());

        return timeSlotMapper.toResponse(saved);
    }
}
