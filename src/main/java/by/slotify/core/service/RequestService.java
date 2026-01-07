package by.slotify.core.service;

import by.slotify.core.dto.request.AcceptRequestRequest;
import by.slotify.core.dto.request.RequestRequest;
import by.slotify.core.dto.response.RequestResponse;
import by.slotify.core.entity.Meeting;
import by.slotify.core.entity.Notification;
import by.slotify.core.entity.ParticipationType;
import by.slotify.core.entity.Request;
import by.slotify.core.entity.TimeSlot;
import by.slotify.core.entity.User;
import by.slotify.core.mapper.RequestMapper;
import by.slotify.core.repository.NotificationRepository;
import by.slotify.core.repository.ParticipationTypeRepository;
import by.slotify.core.repository.RequestRepository;
import by.slotify.core.repository.TimeSlotRepository;
import by.slotify.core.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RequestService {
    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final TimeSlotRepository timeSlotRepository;
    private final ParticipationTypeRepository participationTypeRepository;
    private final NotificationRepository notificationRepository;
    private final RequestMapper requestMapper;

    @Transactional
    public RequestResponse create(RequestRequest requestRequest) {
        Request request = requestMapper.toEntity(requestRequest);
        
        User user = userRepository.findById(requestRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + requestRequest.getUserId()));
        TimeSlot timeSlot = timeSlotRepository.findById(requestRequest.getSlotId())
                .orElseThrow(() -> new RuntimeException("TimeSlot not found with id: " + requestRequest.getSlotId()));
        ParticipationType participationType = participationTypeRepository.findById(requestRequest.getParticipationTypeId())
                .orElseThrow(() -> new RuntimeException("ParticipationType not found with id: " + requestRequest.getParticipationTypeId()));
        
        // Устанавливаем Meeting из TimeSlot для прямой связи
        Meeting meeting = timeSlot.getMeeting();
        if (meeting == null) {
            throw new RuntimeException("TimeSlot does not have associated Meeting");
        }
        
        request.setUser(user);
        request.setTimeSlot(timeSlot);
        request.setMeeting(meeting);
        request.setParticipationType(participationType);
        
        Request saved = requestRepository.save(request);
        return requestMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public Optional<RequestResponse> findById(Integer id) {
        return requestRepository.findById(id)
                .map(requestMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public Page<RequestResponse> findAll(Pageable pageable) {
        return requestRepository.findAll(pageable)
                .map(requestMapper::toResponse);
    }

    @Transactional
    public RequestResponse update(Integer id, RequestRequest requestRequest) {
        Request request = requestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Request not found with id: " + id));
        
        request.setStatus(requestRequest.getStatus());
        
        if (requestRequest.getUserId() != null) {
            User user = userRepository.findById(requestRequest.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + requestRequest.getUserId()));
            request.setUser(user);
        }
        
        if (requestRequest.getSlotId() != null) {
            TimeSlot timeSlot = timeSlotRepository.findById(requestRequest.getSlotId())
                    .orElseThrow(() -> new RuntimeException("TimeSlot not found with id: " + requestRequest.getSlotId()));
            request.setTimeSlot(timeSlot);
            // Обновляем Meeting при изменении TimeSlot
            Meeting meeting = timeSlot.getMeeting();
            if (meeting == null) {
                throw new RuntimeException("TimeSlot does not have associated Meeting");
            }
            request.setMeeting(meeting);
        }
        
        if (requestRequest.getParticipationTypeId() != null) {
            ParticipationType participationType = participationTypeRepository.findById(requestRequest.getParticipationTypeId())
                    .orElseThrow(() -> new RuntimeException("ParticipationType not found with id: " + requestRequest.getParticipationTypeId()));
            request.setParticipationType(participationType);
        }
        
        Request saved = requestRepository.save(request);
        return requestMapper.toResponse(saved);
    }

    @Transactional
    public void deleteById(Integer id) {
        requestRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<RequestResponse> findByUserId(Integer userId, Pageable pageable) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        return requestRepository.findByUser(user, pageable)
                .map(requestMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public Page<RequestResponse> findByMeetingId(Integer meetingId, Pageable pageable) {
        return requestRepository.findByMeeting_MeetingId(meetingId, pageable)
                .map(requestMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public List<RequestResponse> findMyAcceptedRequests(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        return requestRepository.findByUserAndStatus(user, Request.Status.ACCEPTED).stream()
                .map(requestMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public RequestResponse acceptOrRejectRequest(AcceptRequestRequest acceptRequest) {
        Request request = requestRepository.findById(acceptRequest.getRequestId())
                .orElseThrow(() -> new RuntimeException("Request not found with id: " + acceptRequest.getRequestId()));

        Request.Status newStatus = acceptRequest.getAccept() 
                ? Request.Status.ACCEPTED 
                : Request.Status.REJECTED;
        
        request.setStatus(newStatus);
        Request saved = requestRepository.save(request);

        // Создаем уведомление для пользователя
        String message = acceptRequest.getAccept() 
                ? "Your request has been accepted" 
                : "Your request has been rejected";
        
        Notification notification = Notification.builder()
                .user(request.getUser())
                .message(message)
                .type(acceptRequest.getAccept() 
                        ? Notification.Type.CONFIRMATION 
                        : Notification.Type.WARNING)
                .isRead(false)
                .build();
        
        notificationRepository.save(notification);
        log.info("Request {} {} for user {}", acceptRequest.getRequestId(), 
                acceptRequest.getAccept() ? "accepted" : "rejected", 
                request.getUser().getUsername());

        return requestMapper.toResponse(saved);
    }
}
