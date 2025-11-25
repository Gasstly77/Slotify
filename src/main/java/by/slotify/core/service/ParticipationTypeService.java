package by.slotify.core.service;

import by.slotify.core.dto.ParticipationTypeDto;
import by.slotify.core.entity.ParticipationType;
import by.slotify.core.mapper.ParticipationTypeMapper;
import by.slotify.core.repository.ParticipationTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ParticipationTypeService {
    private final ParticipationTypeRepository participationTypeRepository;
    private final ParticipationTypeMapper participationTypeMapper;

    public ParticipationTypeDto create(ParticipationTypeDto participationTypeDto) {
        ParticipationType participationType = participationTypeMapper.toEntity(participationTypeDto);
        ParticipationType saved = participationTypeRepository.save(participationType);
        return participationTypeMapper.toDto(saved);
    }

    public Optional<ParticipationTypeDto> findById(Integer id) {
        return participationTypeRepository.findById(id)
                .map(participationTypeMapper::toDto);
    }

    public List<ParticipationTypeDto> findAll() {
        return participationTypeRepository.findAll().stream()
                .map(participationTypeMapper::toDto)
                .collect(Collectors.toList());
    }

    public ParticipationTypeDto update(ParticipationTypeDto participationTypeDto) {
        ParticipationType participationType = participationTypeMapper.toEntity(participationTypeDto);
        ParticipationType saved = participationTypeRepository.save(participationType);
        return participationTypeMapper.toDto(saved);
    }

    public void deleteById(Integer id) {
        participationTypeRepository.deleteById(id);
    }

    public void delete(ParticipationTypeDto participationTypeDto) {
        ParticipationType participationType = participationTypeMapper.toEntity(participationTypeDto);
        participationTypeRepository.delete(participationType);
    }
}
