package by.slotify.core.controller;

import by.slotify.core.dto.request.LocationRequest;
import by.slotify.core.dto.response.LocationResponse;
import by.slotify.core.service.LocationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/locations")
@RequiredArgsConstructor
@Tag(name = "Locations", description = "API для управления локациями")
public class LocationController {
    private final LocationService locationService;

    @GetMapping
    @Operation(summary = "Получить все локации")
    public List<LocationResponse> getAllLocations() {
        return locationService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить локацию по ID")
    public LocationResponse getLocationById(@PathVariable Integer id) {
        return locationService.findById(id)
                .orElseThrow(() -> new RuntimeException("Location not found with id: " + id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Создать локацию", description = "Создать локацию (только для админа)")
    @ResponseStatus(HttpStatus.CREATED)
    public LocationResponse createLocation(@Valid @RequestBody LocationRequest locationRequest) {
        return locationService.create(locationRequest);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Обновить локацию", description = "Обновить локацию (только для админа)")
    public LocationResponse updateLocation(
            @PathVariable Integer id,
            @Valid @RequestBody LocationRequest locationRequest) {
        return locationService.update(id, locationRequest);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Удалить локацию", description = "Удалить локацию (только для админа)")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLocation(@PathVariable Integer id) {
        locationService.deleteById(id);
    }
}
