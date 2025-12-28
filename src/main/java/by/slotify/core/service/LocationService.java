package by.slotify.core.service;

import by.slotify.core.dto.request.LocationRequest;
import by.slotify.core.dto.response.LocationResponse;
import by.slotify.core.entity.Location;
import by.slotify.core.mapper.LocationMapper;
import by.slotify.core.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LocationService {
    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;

    @Transactional
    public LocationResponse create(LocationRequest locationRequest) {
        Location location = locationMapper.toEntity(locationRequest);
        Location saved = locationRepository.save(location);
        return locationMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public Optional<LocationResponse> findById(Integer id) {
        return locationRepository.findById(id)
                .map(locationMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public java.util.List<LocationResponse> findAll() {
        return locationRepository.findAll().stream()
                .map(locationMapper::toResponse)
                .toList();
    }

    @Transactional
    public LocationResponse update(Integer id, LocationRequest locationRequest) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Location not found with id: " + id));
        
        location.setName(locationRequest.getName());
        location.setDetails(locationRequest.getDetails());
        
        Location saved = locationRepository.save(location);
        return locationMapper.toResponse(saved);
    }

    @Transactional
    public void deleteById(Integer id) {
        locationRepository.deleteById(id);
    }
}
