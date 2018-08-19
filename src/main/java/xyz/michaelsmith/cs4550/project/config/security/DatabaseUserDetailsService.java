package xyz.michaelsmith.cs4550.project.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import xyz.michaelsmith.cs4550.project.user.data.UserRepository;

@Component
public class DatabaseUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public DatabaseUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new DatabaseUserDetails(userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username)));
    }
}
