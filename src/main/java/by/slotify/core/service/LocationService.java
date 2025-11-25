package by.slotify.core.service;

import by.slotify.core.dto.LocationDto;
import by.slotify.core.entity.Location;
import by.slotify.core.mapper.LocationMapper;
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
public class LocationService {
    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;

    public LocationDto create(LocationDto locationDto) {
        Location location = locationMapper.toEntity(locationDto);
        Location saved = locationRepository.save(location);
        return locationMapper.toDto(saved);
    }

    public Optional<LocationDto> findById(Integer id) {
        return locationRepository.findById(id)
                .map(locationMapper::toDto);
    }

    public List<LocationDto> findAll() {
        return locationRepository.findAll().stream()
                .map(locationMapper::toDto)
                .collect(Collectors.toList());
    }

    public LocationDto update(LocationDto locationDto) {
        Location location = locationMapper.toEntity(locationDto);
        Location saved = locationRepository.save(location);
        return locationMapper.toDto(saved);
    }

    public void deleteById(Integer id) {
        locationRepository.deleteById(id);
    }

    public void delete(LocationDto locationDto) {
        Location location = locationMapper.toEntity(locationDto);
        locationRepository.delete(location);
    }
}
