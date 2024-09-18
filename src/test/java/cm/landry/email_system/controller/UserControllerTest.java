package cm.landry.email_system.controller;

import cm.landry.email_system.entity.User;
import cm.landry.email_system.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    public UserControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetUserByUsername_UserFound() throws Exception {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password");
        user.setEmail("testuser@example.com");

        when(userService.findByUsername("testuser")).thenReturn(user);

        mockMvc.perform(get("/users/username/testuser")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("testuser")))
                .andExpect(jsonPath("$.email", is("testuser@example.com")));
    }

    @Test
    public void testGetUserByUsername_UserNotFound() throws Exception {
        when(userService.findByUsername("nonexistentuser")).thenReturn(null);

        mockMvc.perform(get("/users/username/nonexistentuser")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetUserById_UserFound() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setPassword("password");
        user.setEmail("testuser@example.com");

        when(userService.findById(1L)).thenReturn(user);

        mockMvc.perform(get("/users/id/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("testuser")))
                .andExpect(jsonPath("$.email", is("testuser@example.com")));
    }

    @Test
    public void testGetUserById_UserNotFound() throws Exception {
        when(userService.findById(1L)).thenReturn(null);

        mockMvc.perform(get("/users/id/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetUserByEmail_UserFound() throws Exception {
        User user = new User();
        user.setEmail("testuser@example.com");
        user.setUsername("testuser");
        user.setPassword("password");

        when(userService.findByEmail("testuser@example.com")).thenReturn(user);

        mockMvc.perform(get("/users/email/testuser@example.com")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is("testuser@example.com")))
                .andExpect(jsonPath("$.username", is("testuser")));
    }

    @Test
    public void testGetUserByEmail_UserNotFound() throws Exception {
        when(userService.findByEmail("nonexistent@example.com")).thenReturn(null);

        mockMvc.perform(get("/users/email/nonexistent@example.com")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateUser() throws Exception {
        User user = new User();
        user.setUsername("newuser");
        user.setPassword("password");
        user.setEmail("newuser@example.com");

        when(userService.createUser(user)).thenReturn(user);

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"newuser\",\"password\":\"password\",\"email\":\"newuser@example.com\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username", is("newuser")))
                .andExpect(jsonPath("$.email", is("newuser@example.com")));
    }

    @Test
    public void testUpdateUser() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setUsername("updateduser");
        user.setPassword("newpassword");
        user.setEmail("updateduser@example.com");

        when(userService.updateUser(1L, user)).thenReturn(user);

        mockMvc.perform(put("/users/id/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"updateduser\",\"password\":\"newpassword\",\"email\":\"updateduser@example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("updateduser")))
                .andExpect(jsonPath("$.email", is("updateduser@example.com")));
    }

    @Test
    public void testUpdateUser_UserNotFound() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setUsername("updateduser");
        user.setPassword("newpassword");
        user.setEmail("updateduser@example.com");

        when(userService.updateUser(1L, user)).thenReturn(null);

        mockMvc.perform(put("/users/id/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"updateduser\",\"password\":\"newpassword\",\"email\":\"updateduser@example.com\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetAllUsersOrderedByDateDesc() throws Exception {
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

        when(userService.getAllUsersOrderedByDateDesc()).thenReturn(users);

        mockMvc.perform(get("/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username", is("user2")))
                .andExpect(jsonPath("$[1].username", is("user1")));
    }
}
