package by.slotify.core.util;

import by.slotify.core.entity.User;
import by.slotify.core.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SecurityUtil {

    private final UserRepository userRepository;

    public Optional<User> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            return userRepository.findByUsername(username);
        }
        return Optional.empty();
    }

    public Integer getCurrentUserId() {
        return getCurrentUser()
                .map(User::getUserId)
                .orElseThrow(() -> new RuntimeException("User not authenticated"));
    }

    public boolean isAdmin() {
        return getCurrentUser()
                .map(user -> user.getRole() == User.Role.ADMIN)
                .orElse(false);
    }
}

