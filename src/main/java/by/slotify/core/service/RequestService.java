package by.slotify.core.service;

import by.slotify.core.dto.RequestDto;
import by.slotify.core.entity.ParticipationType;
import by.slotify.core.entity.Request;
import by.slotify.core.entity.TimeSlot;
import by.slotify.core.entity.User;
import by.slotify.core.mapper.RequestMapper;
import by.slotify.core.repository.ParticipationTypeRepository;
import by.slotify.core.repository.RequestRepository;
import by.slotify.core.repository.TimeSlotRepository;
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
public class RequestService {
    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final TimeSlotRepository timeSlotRepository;
    private final ParticipationTypeRepository participationTypeRepository;
    private final RequestMapper requestMapper;

    public RequestDto create(RequestDto requestDto) {
        Request request = requestMapper.toEntity(requestDto);
        

        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + requestDto.getUserId()));
        TimeSlot timeSlot = timeSlotRepository.findById(requestDto.getSlotId())
                .orElseThrow(() -> new RuntimeException("TimeSlot not found with id: " + requestDto.getSlotId()));
        ParticipationType participationType = participationTypeRepository.findById(requestDto.getParticipationTypeId())
                .orElseThrow(() -> new RuntimeException("ParticipationType not found with id: " + requestDto.getParticipationTypeId()));
        
        request.setUser(user);
        request.setTimeSlot(timeSlot);
        request.setParticipationType(participationType);
        
        Request saved = requestRepository.save(request);
        return requestMapper.toDto(saved);
    }

    public Optional<RequestDto> findById(Integer id) {
        return requestRepository.findById(id)
                .map(requestMapper::toDto);
    }

    public List<RequestDto> findAll() {
        return requestRepository.findAll().stream()
                .map(requestMapper::toDto)
                .collect(Collectors.toList());
    }

    public RequestDto update(RequestDto requestDto) {
        Request request = requestRepository.findById(requestDto.getRequestId())
                .orElseThrow(() -> new RuntimeException("Request not found with id: " + requestDto.getRequestId()));
        
        request.setStatus(requestDto.getStatus());
        
        if (requestDto.getUserId() != null) {
            User user = userRepository.findById(requestDto.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + requestDto.getUserId()));
            request.setUser(user);
        }
        
        if (requestDto.getSlotId() != null) {
            TimeSlot timeSlot = timeSlotRepository.findById(requestDto.getSlotId())
                    .orElseThrow(() -> new RuntimeException("TimeSlot not found with id: " + requestDto.getSlotId()));
            request.setTimeSlot(timeSlot);
        }
        
        if (requestDto.getParticipationTypeId() != null) {
            ParticipationType participationType = participationTypeRepository.findById(requestDto.getParticipationTypeId())
                    .orElseThrow(() -> new RuntimeException("ParticipationType not found with id: " + requestDto.getParticipationTypeId()));
            request.setParticipationType(participationType);
        }
        
        Request saved = requestRepository.save(request);
        return requestMapper.toDto(saved);
    }

    public void deleteById(Integer id) {
        requestRepository.deleteById(id);
    }

    public void delete(RequestDto requestDto) {
        Request request = requestMapper.toEntity(requestDto);
        requestRepository.delete(request);
    }
}
