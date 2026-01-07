package by.slotify.core.service;

import by.slotify.core.dto.request.NotificationRequest;
import by.slotify.core.dto.response.NotificationResponse;
import by.slotify.core.entity.Notification;
import by.slotify.core.entity.User;
import by.slotify.core.mapper.NotificationMapper;
import by.slotify.core.repository.NotificationRepository;
import by.slotify.core.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final NotificationMapper notificationMapper;

    @Transactional
    public NotificationResponse create(NotificationRequest notificationRequest) {
        Notification notification = notificationMapper.toEntity(notificationRequest);
        
        User user = userRepository.findById(notificationRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + notificationRequest.getUserId()));
        
        notification.setUser(user);
        
        Notification saved = notificationRepository.save(notification);
        return notificationMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public Optional<NotificationResponse> findById(Integer id) {
        return notificationRepository.findById(id)
                .map(notificationMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public List<NotificationResponse> findAll() {
        return notificationRepository.findAll().stream()
                .map(notificationMapper::toResponse)
                .toList();
    }

    @Transactional
    public NotificationResponse update(Integer id, NotificationRequest notificationRequest) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found with id: " + id));
        
        notification.setMessage(notificationRequest.getMessage());
        notification.setType(notificationRequest.getType());
        notification.setIsRead(notificationRequest.getIsRead());
        
        if (notificationRequest.getUserId() != null) {
            User user = userRepository.findById(notificationRequest.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + notificationRequest.getUserId()));
            notification.setUser(user);
        }
        
        Notification saved = notificationRepository.save(notification);
        return notificationMapper.toResponse(saved);
    }

    @Transactional
    public void deleteById(Integer id) {
        notificationRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<NotificationResponse> findByUserId(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        return notificationRepository.findByUser(user).stream()
                .map(notificationMapper::toResponse)
                .toList();
    }
}
