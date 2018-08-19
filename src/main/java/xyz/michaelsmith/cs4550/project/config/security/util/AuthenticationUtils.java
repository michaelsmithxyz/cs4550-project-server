package xyz.michaelsmith.cs4550.project.config.security.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import xyz.michaelsmith.cs4550.project.config.security.auth.DatabaseUserDetails;
import xyz.michaelsmith.cs4550.project.user.data.UserRepository;
import xyz.michaelsmith.cs4550.project.user.data.entity.User;

import static java.util.Collections.singletonList;

@Component
public class AuthenticationUtils {
    private UserRepository userRepository;

    @Autowired
    public AuthenticationUtils(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void authUser(User user) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(new DatabaseUserDetails(user), null, singletonList(new SimpleGrantedAuthority(user.getRole().name())));
        SecurityContextHolder.getContext().setAuthentication(token);
    }

    public User getUserFromPrincipal() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof DatabaseUserDetails) {
            return ((DatabaseUserDetails) principal).getUser();
        }
        return (User) principal;
    }

    public void refreshAuthentication() {
        User user = getUserFromPrincipal();
        user = userRepository.findById(user.getId()).orElseThrow(() -> new IllegalArgumentException("You're logged in as a user who doesn't exist"));
        Authentication currentAuthentication = SecurityContextHolder.getContext().getAuthentication();
        UsernamePasswordAuthenticationToken newAuthentication = new UsernamePasswordAuthenticationToken(user, currentAuthentication.getCredentials(), singletonList(new SimpleGrantedAuthority(user.getRole().name())));
        SecurityContextHolder.getContext().setAuthentication(newAuthentication);
    }
}
