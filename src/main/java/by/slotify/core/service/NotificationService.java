package by.slotify.core.service;

import by.slotify.core.dto.NotificationDto;
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
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final NotificationMapper notificationMapper;

    public NotificationDto create(NotificationDto notificationDto) {
        Notification notification = notificationMapper.toEntity(notificationDto);

        User user = userRepository.findById(notificationDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + notificationDto.getUserId()));
        
        notification.setUser(user);
        
        Notification saved = notificationRepository.save(notification);
        return notificationMapper.toDto(saved);
    }

    public Optional<NotificationDto> findById(Integer id) {
        return notificationRepository.findById(id)
                .map(notificationMapper::toDto);
    }

    public List<NotificationDto> findAll() {
        return notificationRepository.findAll().stream()
                .map(notificationMapper::toDto)
                .collect(Collectors.toList());
    }

    public NotificationDto update(NotificationDto notificationDto) {
        Notification notification = notificationRepository.findById(notificationDto.getNotificationId())
                .orElseThrow(() -> new RuntimeException("Notification not found with id: " + notificationDto.getNotificationId()));
        
        notification.setMessage(notificationDto.getMessage());
        notification.setType(notificationDto.getType());
        notification.setIsRead(notificationDto.getIsRead());
        
        if (notificationDto.getUserId() != null) {
            User user = userRepository.findById(notificationDto.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + notificationDto.getUserId()));
            notification.setUser(user);
        }
        
        Notification saved = notificationRepository.save(notification);
        return notificationMapper.toDto(saved);
    }

    public void deleteById(Integer id) {
        notificationRepository.deleteById(id);
    }

    public void delete(NotificationDto notificationDto) {
        Notification notification = notificationMapper.toEntity(notificationDto);
        notificationRepository.delete(notification);
    }
}
