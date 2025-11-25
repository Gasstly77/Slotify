package by.slotify.core.service;

import by.slotify.core.dto.UserDto;
import by.slotify.core.entity.User;
import by.slotify.core.mapper.UserMapper;
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
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserDto create(UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        User saved = userRepository.save(user);
        return userMapper.toDto(saved);
    }

    public Optional<UserDto> findById(Integer id) {
        return userRepository.findById(id)
                .map(userMapper::toDto);
    }

    public List<UserDto> findAll() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    public UserDto update(UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        User saved = userRepository.save(user);
        return userMapper.toDto(saved);
    }

    public void deleteById(Integer id) {
        userRepository.deleteById(id);
    }

    public void delete(UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        userRepository.delete(user);
    }
}
