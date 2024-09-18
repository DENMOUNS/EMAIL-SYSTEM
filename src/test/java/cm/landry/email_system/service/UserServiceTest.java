package cm.landry.email_system.service;

import cm.landry.email_system.entity.User;
import cm.landry.email_system.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    public UserServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateUser() {
        User user = new User();
        user.setUsername("newuser");
        user.setPassword("password");
        user.setEmail("newuser@example.com");

        when(userRepository.save(user)).thenReturn(user);

        User createdUser = userService.createUser(user);
        assertEquals("newuser", createdUser.getUsername());
    }

    @Test
    public void testUpdateUser_UserFound() {
        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setUsername("oldusername");
        existingUser.setPassword("oldpassword");
        existingUser.setEmail("oldemail@example.com");

        User userDetails = new User();
        userDetails.setUsername("updateduser");
        userDetails.setPassword("newpassword");
        userDetails.setEmail("updateduser@example.com");

        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenReturn(existingUser);

        User updatedUser = userService.updateUser(1L, userDetails);
        assertEquals("updateduser", updatedUser.getUsername());
    }

    @Test
    public void testUpdateUser_UserNotFound() {
        User userDetails = new User();
        userDetails.setUsername("updateduser");
        userDetails.setPassword("newpassword");
        userDetails.setEmail("updateduser@example.com");

        when(userRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        User updatedUser = userService.updateUser(1L, userDetails);
        assertNull(updatedUser);
    }

    @Test
    public void testFindByUsername_UserFound() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password");
        user.setEmail("testuser@example.com");

        when(userRepository.findByUsername("testuser")).thenReturn(java.util.Optional.of(user));

        User foundUser = userService.findByUsername("testuser");
        assertEquals("testuser", foundUser.getUsername());
    }

    @Test
    public void testFindByUsername_UserNotFound() {
        when(userRepository.findByUsername("nonexistentuser")).thenReturn(java.util.Optional.empty());

        User foundUser = userService.findByUsername("nonexistentuser");
        assertNull(foundUser);
    }

    @Test
    public void testFindById_UserFound() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setPassword("password");
        user.setEmail("testuser@example.com");

        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(user));

        User foundUser = userService.findById(1L);
        assertEquals("testuser", foundUser.getUsername());
    }

    @Test
    public void testFindById_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        User foundUser = userService.findById(1L);
        assertNull(foundUser);
    }

    @Test
    public void testFindByEmail_UserFound() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password");
        user.setEmail("testuser@example.com");

        when(userRepository.findByEmail("testuser@example.com")).thenReturn(java.util.Optional.of(user));

        User foundUser = userService.findByEmail("testuser@example.com");
        assertEquals("testuser@example.com", foundUser.getEmail());
    }

    @Test
    public void testFindByEmail_UserNotFound() {
        when(userRepository.findByEmail("nonexistent@example.com")).thenReturn(java.util.Optional.empty());

        User foundUser = userService.findByEmail("nonexistent@example.com");
        assertNull(foundUser);
    }

    @Test
    public void testGetAllUsersOrderedByDateDesc() {
        User user1 = new User();
        user1.setId(1L);
        user1.setUsername("user1");
        user1.setPassword("password1");
        user1.setEmail("user1@example.com");
        user1.setCreatedAt(LocalDateTime.now().minusDays(1));

        User user2 = new User();
        user2.setId(2L);
        user2.setUsername("user2");
        user2.setPassword("password2");
        user2.setEmail("user2@example.com");
        user2.setCreatedAt(LocalDateTime.now());

        List<User> users = Arrays.asList(user2, user1);

        when(userRepository.findAllByOrderByCreatedAtDesc()).thenReturn(users);

        List<User> allUsers = userService.getAllUsersOrderedByDateDesc();
        assertEquals(2, allUsers.size());
        assertEquals("user2", allUsers.get(0).getUsername());
        assertEquals("user1", allUsers.get(1).getUsername());
    }
}
