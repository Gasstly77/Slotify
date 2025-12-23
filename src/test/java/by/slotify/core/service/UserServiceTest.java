package by.slotify.core.service;

import by.slotify.core.dto.request.UserRequest;
import by.slotify.core.dto.response.UserResponse;
import by.slotify.core.entity.User;
import by.slotify.core.mapper.UserMapper;
import by.slotify.core.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    private User user;
    private UserRequest userRequest;
    private UserResponse userResponse;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .userId(1)
                .username("testuser")
                .passwordHash("hashedpassword")
                .email("test@example.com")
                .role(User.Role.USER)
                .build();

        userRequest = UserRequest.builder()
                .username("testuser")
                .passwordHash("hashedpassword")
                .email("test@example.com")
                .role(User.Role.USER)
                .build();

        userResponse = UserResponse.builder()
                .userId(1)
                .username("testuser")
                .email("test@example.com")
                .role(User.Role.USER)
                .build();
    }

    @Test
    void testCreate_Success() {
        // Given
        when(userMapper.toEntity(userRequest)).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.toResponse(user)).thenReturn(userResponse);

        // When
        UserResponse result = userService.create(userRequest);

        // Then
        assertNotNull(result);
        assertEquals(userResponse.getUserId(), result.getUserId());
        assertEquals(userResponse.getUsername(), result.getUsername());
        assertEquals(userResponse.getEmail(), result.getEmail());
        verify(userMapper).toEntity(userRequest);
        verify(userRepository).save(user);
        verify(userMapper).toResponse(user);
    }

    @Test
    void testFindById_Success() {
        // Given
        Integer userId = 1;
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userMapper.toResponse(user)).thenReturn(userResponse);

        // When
        Optional<UserResponse> result = userService.findById(userId);

        // Then
        assertTrue(result.isPresent());
        assertEquals(userResponse.getUserId(), result.get().getUserId());
        verify(userRepository).findById(userId);
        verify(userMapper).toResponse(user);
    }

    @Test
    void testFindById_NotFound() {
        // Given
        Integer userId = 999;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // When
        Optional<UserResponse> result = userService.findById(userId);

        // Then
        assertTrue(result.isEmpty());
        verify(userRepository).findById(userId);
        verify(userMapper, never()).toResponse(any());
    }

    @Test
    void testFindAll_Success() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);
        List<User> users = Arrays.asList(user);
        Page<User> userPage = new PageImpl<>(users, pageable, 1);
        
        when(userRepository.findAll(pageable)).thenReturn(userPage);
        when(userMapper.toResponse(user)).thenReturn(userResponse);

        // When
        Page<UserResponse> result = userService.findAll(pageable);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getContent().size());
        assertEquals(userResponse.getUserId(), result.getContent().get(0).getUserId());
        verify(userRepository).findAll(pageable);
        verify(userMapper).toResponse(user);
    }

    @Test
    void testFindAll_Empty() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);
        Page<User> emptyPage = new PageImpl<>(List.of(), pageable, 0);
        when(userRepository.findAll(pageable)).thenReturn(emptyPage);

        // When
        Page<UserResponse> result = userService.findAll(pageable);

        // Then
        assertNotNull(result);
        assertEquals(0, result.getTotalElements());
        assertTrue(result.getContent().isEmpty());
        verify(userRepository).findAll(pageable);
    }

    @Test
    void testUpdate_Success() {
        // Given
        Integer userId = 1;
        UserRequest updateRequest = UserRequest.builder()
                .username("updateduser")
                .passwordHash("newpassword")
                .email("updated@example.com")
                .role(User.Role.ADMIN)
                .build();

        UserResponse updatedResponse = UserResponse.builder()
                .userId(1)
                .username("updateduser")
                .email("updated@example.com")
                .role(User.Role.ADMIN)
                .build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.toResponse(user)).thenReturn(updatedResponse);

        // When
        UserResponse result = userService.update(userId, updateRequest);

        // Then
        assertNotNull(result);
        assertEquals(updateRequest.getUsername(), result.getUsername());
        assertEquals(updateRequest.getEmail(), result.getEmail());
        assertEquals(updateRequest.getRole(), result.getRole());
        verify(userRepository).findById(userId);
        verify(userRepository).save(user);
        verify(userMapper).toResponse(user);
    }

    @Test
    void testUpdate_UserNotFound() {
        // Given
        Integer userId = 999;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.update(userId, userRequest);
        });

        assertEquals("User not found with id: 999", exception.getMessage());
        verify(userRepository).findById(userId);
        verify(userRepository, never()).save(any());
    }

    @Test
    void testDeleteById_Success() {
        // Given
        Integer userId = 1;
        doNothing().when(userRepository).deleteById(userId);

        // When
        userService.deleteById(userId);

        // Then
        verify(userRepository).deleteById(userId);
    }
}

