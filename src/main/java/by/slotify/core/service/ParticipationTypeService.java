package by.slotify.core.service;

import by.slotify.core.dto.request.ParticipationTypeRequest;
import by.slotify.core.dto.response.ParticipationTypeResponse;
import by.slotify.core.entity.ParticipationType;
import by.slotify.core.mapper.ParticipationTypeMapper;
import by.slotify.core.repository.ParticipationTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ParticipationTypeService {
    private final ParticipationTypeRepository participationTypeRepository;
    private final ParticipationTypeMapper participationTypeMapper;

    @Transactional
    public ParticipationTypeResponse create(ParticipationTypeRequest participationTypeRequest) {
        ParticipationType participationType = participationTypeMapper.toEntity(participationTypeRequest);
        ParticipationType saved = participationTypeRepository.save(participationType);
        return participationTypeMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public Optional<ParticipationTypeResponse> findById(Integer id) {
        return participationTypeRepository.findById(id)
                .map(participationTypeMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public List<ParticipationTypeResponse> findAll() {
        return participationTypeRepository.findAll().stream()
                .map(participationTypeMapper::toResponse)
                .toList();
    }

    @Transactional
    public ParticipationTypeResponse update(Integer id, ParticipationTypeRequest participationTypeRequest) {
        ParticipationType participationType = participationTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ParticipationType not found with id: " + id));
        
        participationType.setName(participationTypeRequest.getName());
        participationType.setDescription(participationTypeRequest.getDescription());
        
        ParticipationType saved = participationTypeRepository.save(participationType);
        return participationTypeMapper.toResponse(saved);
    }

    @Transactional
    public void deleteById(Integer id) {
        participationTypeRepository.deleteById(id);
    }
}
