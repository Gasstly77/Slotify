package by.slotify.core.service;

import by.slotify.core.dto.MeetingDto;
import by.slotify.core.entity.Meeting;
import by.slotify.core.entity.MeetingType;
import by.slotify.core.entity.Location;
import by.slotify.core.mapper.MeetingMapper;
import by.slotify.core.repository.MeetingRepository;
import by.slotify.core.repository.MeetingTypeRepository;
import by.slotify.core.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MeetingService {
    private final MeetingRepository meetingRepository;
    private final MeetingTypeRepository meetingTypeRepository;
    private final LocationRepository locationRepository;
    private final MeetingMapper meetingMapper;

    public MeetingDto create(MeetingDto meetingDto) {
        Meeting meeting = meetingMapper.toEntity(meetingDto);

        MeetingType meetingType = meetingTypeRepository.findById(meetingDto.getMeetingTypeId())
                .orElseThrow(() -> new RuntimeException("MeetingType not found with id: " + meetingDto.getMeetingTypeId()));
        Location location = locationRepository.findById(meetingDto.getLocationId())
                .orElseThrow(() -> new RuntimeException("Location not found with id: " + meetingDto.getLocationId()));
        
        meeting.setMeetingType(meetingType);
        meeting.setLocation(location);
        
        Meeting saved = meetingRepository.save(meeting);
        return meetingMapper.toDto(saved);
    }

    public Optional<MeetingDto> findById(Integer id) {
        return meetingRepository.findById(id)
                .map(meetingMapper::toDto);
    }

    public List<MeetingDto> findAll() {
        return meetingRepository.findAll().stream()
                .map(meetingMapper::toDto)
                .collect(Collectors.toList());
    }

    public MeetingDto update(MeetingDto meetingDto) {
        Meeting meeting = meetingRepository.findById(meetingDto.getMeetingId())
                .orElseThrow(() -> new RuntimeException("Meeting not found with id: " + meetingDto.getMeetingId()));
        
        meeting.setTitle(meetingDto.getTitle());
        meeting.setDescription(meetingDto.getDescription());
        meeting.setFinalTime(meetingDto.getFinalTime());
        meeting.setStatus(meetingDto.getStatus());
        
        if (meetingDto.getMeetingTypeId() != null) {
            MeetingType meetingType = meetingTypeRepository.findById(meetingDto.getMeetingTypeId())
                    .orElseThrow(() -> new RuntimeException("MeetingType not found with id: " + meetingDto.getMeetingTypeId()));
            meeting.setMeetingType(meetingType);
        }
        
        if (meetingDto.getLocationId() != null) {
            Location location = locationRepository.findById(meetingDto.getLocationId())
                    .orElseThrow(() -> new RuntimeException("Location not found with id: " + meetingDto.getLocationId()));
            meeting.setLocation(location);
        }
        
        Meeting saved = meetingRepository.save(meeting);
        return meetingMapper.toDto(saved);
    }

    public void deleteById(Integer id) {
        meetingRepository.deleteById(id);
    }

    public void delete(MeetingDto meetingDto) {
        Meeting meeting = meetingMapper.toEntity(meetingDto);
        meetingRepository.delete(meeting);
    }
}
