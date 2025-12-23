package by.slotify.core.controller;

import by.slotify.core.dto.request.LocationRequest;
import by.slotify.core.dto.response.LocationResponse;
import by.slotify.core.service.LocationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<LocationResponse>> getAllLocations() {
        List<LocationResponse> locations = locationService.findAll();
        return ResponseEntity.ok(locations);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить локацию по ID")
    public ResponseEntity<LocationResponse> getLocationById(@PathVariable Integer id) {
        return locationService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Создать локацию", description = "Создать локацию (только для админа)")
    public ResponseEntity<LocationResponse> createLocation(@Valid @RequestBody LocationRequest locationRequest) {
        LocationResponse created = locationService.create(locationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Обновить локацию", description = "Обновить локацию (только для админа)")
    public ResponseEntity<LocationResponse> updateLocation(
            @PathVariable Integer id,
            @Valid @RequestBody LocationRequest locationRequest) {
        LocationResponse updated = locationService.update(id, locationRequest);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Удалить локацию", description = "Удалить локацию (только для админа)")
    public ResponseEntity<Void> deleteLocation(@PathVariable Integer id) {
        locationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
