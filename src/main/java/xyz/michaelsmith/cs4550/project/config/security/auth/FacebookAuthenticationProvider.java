package xyz.michaelsmith.cs4550.project.config.security.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.social.ApiException;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.ImageType;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import xyz.michaelsmith.cs4550.project.user.data.UserRepository;

import java.util.Date;

import static java.util.Collections.singletonList;

public class FacebookAuthenticationProvider implements AuthenticationProvider {
    Logger logger = LoggerFactory.getLogger(FacebookAuthenticationProvider.class);

    private final UserRepository userRepository;

    public FacebookAuthenticationProvider(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String fbId = authentication.getName();
        String fbToken = authentication.getCredentials().toString();

        if (fbId == null || fbToken == null) {
            throw new BadCredentialsException("Invalid Facebook id or token");
        }

        Facebook facebookApi = new FacebookTemplate(fbToken);
        try {
            String [] fields = { "id", "email", "name" };
            User facebookUser = facebookApi.fetchObject("me", User.class, fields);
            if (facebookUser.getId().equalsIgnoreCase(fbId)) {
                xyz.michaelsmith.cs4550.project.user.data.entity.User appUser = getOrCreateAppUser(facebookUser, facebookApi);
                return new UsernamePasswordAuthenticationToken(appUser, fbToken, singletonList(new SimpleGrantedAuthority(appUser.getRole().name())));
            }
            throw new BadCredentialsException("Cannot authenticate to Facebook");
        } catch (Exception ex) {
            logger.debug(ex.getMessage());
            throw new BadCredentialsException(ex.getMessage());
        }
    }


    private xyz.michaelsmith.cs4550.project.user.data.entity.User getOrCreateAppUser(User facebookUser, Facebook facebook) {
        xyz.michaelsmith.cs4550.project.user.data.entity.User appUser = userRepository.getByFacebookId(facebookUser.getId());
        if (appUser == null) {
            appUser = new xyz.michaelsmith.cs4550.project.user.data.entity.User();
            appUser.setFacebookId(facebookUser.getId());
            appUser.setName(facebookUser.getName());
            appUser.setEmail(facebookUser.getEmail());
            appUser.setProfilePicture(facebook.userOperations().getUserProfileImage(ImageType.LARGE));
            appUser.setJoined(new Date());
            return userRepository.save(appUser);
        }
        return appUser;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(aClass);
    }
}
