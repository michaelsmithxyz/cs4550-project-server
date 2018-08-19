package xyz.michaelsmith.cs4550.project.config.security.util;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import xyz.michaelsmith.cs4550.project.config.security.auth.DatabaseUserDetails;
import xyz.michaelsmith.cs4550.project.user.data.entity.User;

import static java.util.Collections.singletonList;

@Component
public class AuthenticationUtils {
    public void authUser(User user) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(new DatabaseUserDetails(user), null, singletonList(new SimpleGrantedAuthority(user.getRole().name())));
        SecurityContextHolder.getContext().setAuthentication(token);
    }
}
