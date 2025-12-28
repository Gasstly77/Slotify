package by.slotify.core.service;

import by.slotify.core.dto.request.LoginRequest;
import by.slotify.core.dto.request.RefreshTokenRequest;
import by.slotify.core.dto.request.RegisterRequest;
import by.slotify.core.dto.response.AuthResponse;
import by.slotify.core.entity.RefreshToken;
import by.slotify.core.entity.User;
import by.slotify.core.exception.UserAlreadyExistsException;
import by.slotify.core.repository.RefreshTokenRepository;
import by.slotify.core.repository.UserRepository;
import by.slotify.core.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public AuthResponse login(LoginRequest loginRequest) {
        log.info("Attempting login for user: {}", loginRequest.getUsername());
        
        User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPasswordHash())) {
            log.warn("Invalid password for user: {}", loginRequest.getUsername());
            throw new RuntimeException("Invalid username or password");
        }

        String accessToken = jwtUtil.generateToken(user.getUsername(), user.getRole().name());
        String refreshToken = jwtUtil.generateRefreshToken(user.getUsername());

        // Сохраняем refresh token в БД
        saveRefreshToken(user, refreshToken);

        log.info("User {} successfully logged in", loginRequest.getUsername());
        
        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Transactional
    public AuthResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        log.info("Refreshing token");
        
        String refreshToken = refreshTokenRequest.getRefreshToken();
        
        RefreshToken token = refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        if (token.getExpiryDate().isBefore(LocalDateTime.now())) {
            log.warn("Refresh token expired");
            refreshTokenRepository.delete(token);
            throw new RuntimeException("Refresh token expired");
        }

        User user = token.getUser();
        String newAccessToken = jwtUtil.generateToken(user.getUsername(), user.getRole().name());
        String newRefreshToken = jwtUtil.generateRefreshToken(user.getUsername());

        // Удаляем старый токен и сохраняем новый
        refreshTokenRepository.delete(token);
        saveRefreshToken(user, newRefreshToken);

        log.info("Token refreshed for user: {}", user.getUsername());
        
        return AuthResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .build();
    }

    @Transactional
    public void logout(String refreshToken) {
        log.info("Logging out user");
        refreshTokenRepository.findByToken(refreshToken)
                .ifPresent(refreshTokenRepository::delete);
    }

    @Transactional
    public AuthResponse register(RegisterRequest registerRequest) {
        log.info("Attempting registration for user: {}", registerRequest.getUsername());
        
        // Проверяем, существует ли пользователь с таким username
        if (userRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
            log.warn("Registration failed: username {} already exists", registerRequest.getUsername());
            throw new UserAlreadyExistsException("Пользователь с таким именем уже существует");
        }
        
        // Проверяем, существует ли пользователь с таким email
        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            log.warn("Registration failed: email {} already exists", registerRequest.getEmail());
            throw new UserAlreadyExistsException("Пользователь с таким email уже существует");
        }
        
        // Создаем нового пользователя с ролью USER
        User newUser = User.builder()
                .username(registerRequest.getUsername())
                .passwordHash(passwordEncoder.encode(registerRequest.getPassword()))
                .email(registerRequest.getEmail())
                .role(User.Role.USER)
                .build();
        
        User savedUser = userRepository.save(newUser);
        log.info("User {} successfully registered", savedUser.getUsername());
        
        // Генерируем токены для автоматического входа
        String accessToken = jwtUtil.generateToken(savedUser.getUsername(), savedUser.getRole().name());
        String refreshToken = jwtUtil.generateRefreshToken(savedUser.getUsername());
        
        // Сохраняем refresh token в БД
        saveRefreshToken(savedUser, refreshToken);
        
        log.info("User {} automatically logged in after registration", savedUser.getUsername());
        
        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void saveRefreshToken(User user, String token) {
        // Удаляем старые токены пользователя
        refreshTokenRepository.deleteByUser(user);

        // Создаем новый токен
        LocalDateTime expiryDate = LocalDateTime.now().plusDays(7);
        RefreshToken refreshToken = RefreshToken.builder()
                .token(token)
                .user(user)
                .expiryDate(expiryDate)
                .build();

        refreshTokenRepository.save(refreshToken);
    }
}

