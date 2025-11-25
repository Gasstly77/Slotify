package by.slotify.core.service;

import by.slotify.core.dto.MeetingTypeDto;
import by.slotify.core.entity.MeetingType;
import by.slotify.core.mapper.MeetingTypeMapper;
import by.slotify.core.repository.MeetingTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MeetingTypeService {
    private final MeetingTypeRepository meetingTypeRepository;
    private final MeetingTypeMapper meetingTypeMapper;

    public MeetingTypeDto create(MeetingTypeDto meetingTypeDto) {
        MeetingType meetingType = meetingTypeMapper.toEntity(meetingTypeDto);
        MeetingType saved = meetingTypeRepository.save(meetingType);
        return meetingTypeMapper.toDto(saved);
    }

    public Optional<MeetingTypeDto> findById(Integer id) {
        return meetingTypeRepository.findById(id)
                .map(meetingTypeMapper::toDto);
    }

    public List<MeetingTypeDto> findAll() {
        return meetingTypeRepository.findAll().stream()
                .map(meetingTypeMapper::toDto)
                .collect(Collectors.toList());
    }

    public MeetingTypeDto update(MeetingTypeDto meetingTypeDto) {
        MeetingType meetingType = meetingTypeMapper.toEntity(meetingTypeDto);
        MeetingType saved = meetingTypeRepository.save(meetingType);
        return meetingTypeMapper.toDto(saved);
    }

    public void deleteById(Integer id) {
        meetingTypeRepository.deleteById(id);
    }

    public void delete(MeetingTypeDto meetingTypeDto) {
        MeetingType meetingType = meetingTypeMapper.toEntity(meetingTypeDto);
        meetingTypeRepository.delete(meetingType);
    }
}
