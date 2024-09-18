package cm.landry.email_system.service;

import cm.landry.email_system.entity.User;
import cm.landry.email_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(Long id, User userDetails) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setUsername(userDetails.getUsername());
                    user.setPassword(userDetails.getPassword());
                    user.setEmail(userDetails.getEmail());
                    return userRepository.save(user);
                })
                .orElse(null); // Return null if the user is not found
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElse(null); // Return null if the user is not found
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null); // Return null if the user is not found
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElse(null); // Return null if the user is not found
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public List<User> getAllUsersOrderedByDateDesc() {
        return userRepository.findAllByOrderByCreatedAtDesc();
    }
}
