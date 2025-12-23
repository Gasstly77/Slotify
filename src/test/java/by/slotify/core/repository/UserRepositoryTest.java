package by.slotify.core.repository;

import by.slotify.core.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .username("testuser")
                .passwordHash("hashedpassword")
                .email("test@example.com")
                .role(User.Role.USER)
                .build();
    }

    @Test
    void testSave_Success() {
        // When
        User saved = userRepository.save(user);

        // Then
        assertNotNull(saved.getUserId());
        assertEquals(user.getUsername(), saved.getUsername());
        assertEquals(user.getEmail(), saved.getEmail());
        assertEquals(user.getRole(), saved.getRole());
    }

    @Test
    void testFindById_Success() {
        // Given
        User saved = entityManager.persistAndFlush(user);

        // When
        Optional<User> found = userRepository.findById(saved.getUserId());

        // Then
        assertTrue(found.isPresent());
        assertEquals(saved.getUserId(), found.get().getUserId());
        assertEquals(saved.getUsername(), found.get().getUsername());
    }

    @Test
    void testFindById_NotFound() {
        // When
        Optional<User> found = userRepository.findById(999);

        // Then
        assertTrue(found.isEmpty());
    }

    @Test
    void testFindByUsername_Success() {
        // Given
        User saved = entityManager.persistAndFlush(user);

        // When
        Optional<User> found = userRepository.findByUsername("testuser");

        // Then
        assertTrue(found.isPresent());
        assertEquals(saved.getUsername(), found.get().getUsername());
        assertEquals(saved.getUserId(), found.get().getUserId());
    }

    @Test
    void testFindByUsername_NotFound() {
        // When
        Optional<User> found = userRepository.findByUsername("nonexistent");

        // Then
        assertTrue(found.isEmpty());
    }

    @Test
    void testDelete_Success() {
        // Given
        User saved = entityManager.persistAndFlush(user);
        Integer userId = saved.getUserId();

        // When
        userRepository.deleteById(userId);

        // Then
        Optional<User> found = userRepository.findById(userId);
        assertTrue(found.isEmpty());
    }

    @Test
    void testUpdate_Success() {
        // Given
        User saved = entityManager.persistAndFlush(user);
        saved.setEmail("updated@example.com");
        saved.setRole(User.Role.ADMIN);

        // When
        User updated = userRepository.save(saved);

        // Then
        assertEquals("updated@example.com", updated.getEmail());
        assertEquals(User.Role.ADMIN, updated.getRole());
    }
}

