package by.slotify.core.service;

import by.slotify.core.dto.request.MeetingRequest;
import by.slotify.core.dto.response.MeetingResponse;
import by.slotify.core.entity.Meeting;
import by.slotify.core.entity.MeetingType;
import by.slotify.core.entity.Location;
import by.slotify.core.mapper.MeetingMapper;
import by.slotify.core.repository.MeetingRepository;
import by.slotify.core.repository.MeetingTypeRepository;
import by.slotify.core.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MeetingService {
    private final MeetingRepository meetingRepository;
    private final MeetingTypeRepository meetingTypeRepository;
    private final LocationRepository locationRepository;
    private final MeetingMapper meetingMapper;

    @Transactional
    public MeetingResponse create(MeetingRequest meetingRequest) {
        Meeting meeting = meetingMapper.toEntity(meetingRequest);
        
        MeetingType meetingType = meetingTypeRepository.findById(meetingRequest.getMeetingTypeId())
                .orElseThrow(() -> new RuntimeException("MeetingType not found with id: " + meetingRequest.getMeetingTypeId()));
        Location location = locationRepository.findById(meetingRequest.getLocationId())
                .orElseThrow(() -> new RuntimeException("Location not found with id: " + meetingRequest.getLocationId()));
        
        meeting.setMeetingType(meetingType);
        meeting.setLocation(location);
        
        Meeting saved = meetingRepository.save(meeting);
        return meetingMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public Optional<MeetingResponse> findById(Integer id) {
        return meetingRepository.findById(id)
                .map(meetingMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public Page<MeetingResponse> findAll(Pageable pageable) {
        return meetingRepository.findAll(pageable)
                .map(meetingMapper::toResponse);
    }

    @Transactional
    public MeetingResponse update(Integer id, MeetingRequest meetingRequest) {
        Meeting meeting = meetingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Meeting not found with id: " + id));
        
        meeting.setTitle(meetingRequest.getTitle());
        meeting.setDescription(meetingRequest.getDescription());
        meeting.setFinalTime(meetingRequest.getFinalTime());
        meeting.setStatus(meetingRequest.getStatus());
        
        if (meetingRequest.getMeetingTypeId() != null) {
            MeetingType meetingType = meetingTypeRepository.findById(meetingRequest.getMeetingTypeId())
                    .orElseThrow(() -> new RuntimeException("MeetingType not found with id: " + meetingRequest.getMeetingTypeId()));
            meeting.setMeetingType(meetingType);
        }
        
        if (meetingRequest.getLocationId() != null) {
            Location location = locationRepository.findById(meetingRequest.getLocationId())
                    .orElseThrow(() -> new RuntimeException("Location not found with id: " + meetingRequest.getLocationId()));
            meeting.setLocation(location);
        }
        
        Meeting saved = meetingRepository.save(meeting);
        return meetingMapper.toResponse(saved);
    }

    @Transactional
    public void deleteById(Integer id) {
        meetingRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<MeetingResponse> findActiveMeetings(Pageable pageable) {
        // Активные мероприятия - это PLANNED и CONFIRMED
        // Используем запрос в БД вместо фильтрации в памяти
        List<Meeting.Status> activeStatuses = List.of(Meeting.Status.PLANNED, Meeting.Status.CONFIRMED);
        return meetingRepository.findByStatusIn(activeStatuses, pageable)
                .map(meetingMapper::toResponse);
    }
}
