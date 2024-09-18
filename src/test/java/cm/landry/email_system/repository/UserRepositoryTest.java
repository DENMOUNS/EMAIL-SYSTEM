package cm.landry.email_system.repository;

import cm.landry.email_system.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@Transactional
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindByUsername() {
        // Arrange
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password");
        user.setEmail("testuser@example.com");
        userRepository.save(user);

        // Act
        Optional<User> foundUser = userRepository.findByUsername("testuser");

        // Assert
        assertTrue(foundUser.isPresent(), "User should be found");
        assertEquals("testuser", foundUser.get().getUsername(), "Username should match");
    }

    @Test
    public void testFindByEmail() {
        // Arrange
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password");
        user.setEmail("testuser@example.com");
        userRepository.save(user);

        // Act
        Optional<User> foundUser = userRepository.findByEmail("testuser@example.com");

        // Assert
        assertTrue(foundUser.isPresent(), "User should be found");
        assertEquals("testuser@example.com", foundUser.get().getEmail(), "Email should match");
    }
}
