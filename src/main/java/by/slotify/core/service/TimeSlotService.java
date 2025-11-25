package by.slotify.core.service;

import by.slotify.core.dto.TimeSlotDto;
import by.slotify.core.entity.Meeting;
import by.slotify.core.entity.TimeSlot;
import by.slotify.core.mapper.TimeSlotMapper;
import by.slotify.core.repository.MeetingRepository;
import by.slotify.core.repository.TimeSlotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TimeSlotService {
    private final TimeSlotRepository timeSlotRepository;
    private final MeetingRepository meetingRepository;
    private final TimeSlotMapper timeSlotMapper;

    public TimeSlotDto create(TimeSlotDto timeSlotDto) {
        TimeSlot timeSlot = timeSlotMapper.toEntity(timeSlotDto);

        Meeting meeting = meetingRepository.findById(timeSlotDto.getMeetingId())
                .orElseThrow(() -> new RuntimeException("Meeting not found with id: " + timeSlotDto.getMeetingId()));
        
        timeSlot.setMeeting(meeting);
        
        TimeSlot saved = timeSlotRepository.save(timeSlot);
        return timeSlotMapper.toDto(saved);
    }

    public Optional<TimeSlotDto> findById(Integer id) {
        return timeSlotRepository.findById(id)
                .map(timeSlotMapper::toDto);
    }

    public List<TimeSlotDto> findAll() {
        return timeSlotRepository.findAll().stream()
                .map(timeSlotMapper::toDto)
                .collect(Collectors.toList());
    }

    public TimeSlotDto update(TimeSlotDto timeSlotDto) {
        TimeSlot timeSlot = timeSlotRepository.findById(timeSlotDto.getSlotId())
                .orElseThrow(() -> new RuntimeException("TimeSlot not found with id: " + timeSlotDto.getSlotId()));
        
        timeSlot.setStartTime(timeSlotDto.getStartTime());
        timeSlot.setEndTime(timeSlotDto.getEndTime());
        timeSlot.setIsFinal(timeSlotDto.getIsFinal());
        
        if (timeSlotDto.getMeetingId() != null) {
            Meeting meeting = meetingRepository.findById(timeSlotDto.getMeetingId())
                    .orElseThrow(() -> new RuntimeException("Meeting not found with id: " + timeSlotDto.getMeetingId()));
            timeSlot.setMeeting(meeting);
        }
        
        TimeSlot saved = timeSlotRepository.save(timeSlot);
        return timeSlotMapper.toDto(saved);
    }

    public void deleteById(Integer id) {
        timeSlotRepository.deleteById(id);
    }

    public void delete(TimeSlotDto timeSlotDto) {
        TimeSlot timeSlot = timeSlotMapper.toEntity(timeSlotDto);
        timeSlotRepository.delete(timeSlot);
    }
}
