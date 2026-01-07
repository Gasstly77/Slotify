package by.slotify.core.service;

import by.slotify.core.dto.request.UserRequest;
import by.slotify.core.dto.response.UserResponse;
import by.slotify.core.entity.User;
import by.slotify.core.mapper.UserMapper;
import by.slotify.core.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    public UserResponse create(UserRequest userRequest) {
        User user = userMapper.toEntity(userRequest);
        User saved = userRepository.save(user);
        return userMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public Optional<UserResponse> findById(Integer id) {
        return userRepository.findById(id)
                .map(userMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public Page<UserResponse> findAll(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(userMapper::toResponse);
    }

    @Transactional
    public UserResponse update(Integer id, UserRequest userRequest) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        
        user.setUsername(userRequest.getUsername());
        user.setPasswordHash(userRequest.getPasswordHash());
        user.setEmail(userRequest.getEmail());
        user.setRole(userRequest.getRole());
        
        User saved = userRepository.save(user);
        return userMapper.toResponse(saved);
    }

    @Transactional
    public void deleteById(Integer id) {
        userRepository.deleteById(id);
    }
}
