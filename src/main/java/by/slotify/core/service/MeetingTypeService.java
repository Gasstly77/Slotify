package by.slotify.core.service;

import by.slotify.core.dto.request.MeetingTypeRequest;
import by.slotify.core.dto.response.MeetingTypeResponse;
import by.slotify.core.entity.MeetingType;
import by.slotify.core.mapper.MeetingTypeMapper;
import by.slotify.core.repository.MeetingTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MeetingTypeService {
    private final MeetingTypeRepository meetingTypeRepository;
    private final MeetingTypeMapper meetingTypeMapper;

    @Transactional
    public MeetingTypeResponse create(MeetingTypeRequest meetingTypeRequest) {
        MeetingType meetingType = meetingTypeMapper.toEntity(meetingTypeRequest);
        MeetingType saved = meetingTypeRepository.save(meetingType);
        return meetingTypeMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public Optional<MeetingTypeResponse> findById(Integer id) {
        return meetingTypeRepository.findById(id)
                .map(meetingTypeMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public List<MeetingTypeResponse> findAll() {
        return meetingTypeRepository.findAll().stream()
                .map(meetingTypeMapper::toResponse)
                .toList();
    }

    @Transactional
    public MeetingTypeResponse update(Integer id, MeetingTypeRequest meetingTypeRequest) {
        MeetingType meetingType = meetingTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("MeetingType not found with id: " + id));
        
        meetingType.setName(meetingTypeRequest.getName());
        meetingType.setDescription(meetingTypeRequest.getDescription());
        
        MeetingType saved = meetingTypeRepository.save(meetingType);
        return meetingTypeMapper.toResponse(saved);
    }

    @Transactional
    public void deleteById(Integer id) {
        meetingTypeRepository.deleteById(id);
    }
}
