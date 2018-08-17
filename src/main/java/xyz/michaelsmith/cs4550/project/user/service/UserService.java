package xyz.michaelsmith.cs4550.project.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.michaelsmith.cs4550.project.user.data.UserRepository;
import xyz.michaelsmith.cs4550.project.user.data.entity.User;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public byte[] getUserProfilePicture(Long userId) {
        return userRepository.findById(userId).map(User::getProfilePicture).orElse(null);
    }
}
