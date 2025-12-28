package by.slotify.core.controller;

import by.slotify.core.dto.request.UserRequest;
import by.slotify.core.dto.response.UserResponse;
import by.slotify.core.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "API для управления пользователями")
public class UserController {
    private final UserService userService;

    @GetMapping
    @Operation(summary = "Получить всех пользователей", description = "Возвращает страницу пользователей с пагинацией")
    public Page<UserResponse> getAllUsers(
            @PageableDefault(size = 20, sort = "userId") Pageable pageable) {
        return userService.findAll(pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить пользователя по ID", description = "Возвращает пользователя по указанному ID")
    public UserResponse getUserById(@PathVariable Integer id) {
        return userService.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    @PostMapping
    @Operation(summary = "Создать пользователя", description = "Создает нового пользователя")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse createUser(@Valid @RequestBody UserRequest userRequest) {
        return userService.create(userRequest);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить пользователя", description = "Обновляет существующего пользователя")
    public UserResponse updateUser(
            @PathVariable Integer id,
            @Valid @RequestBody UserRequest userRequest) {
        return userService.update(id, userRequest);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить пользователя", description = "Удаляет пользователя по указанному ID")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Integer id) {
        userService.deleteById(id);
    }
}
