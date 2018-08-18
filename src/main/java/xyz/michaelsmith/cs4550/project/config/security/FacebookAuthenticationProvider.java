package xyz.michaelsmith.cs4550.project.config.security;

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

import static java.util.Collections.singletonList;

public class FacebookAuthenticationProvider implements AuthenticationProvider {

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
        } catch (ApiException ex) {
            throw new BadCredentialsException(ex.getMessage());
        }
    }


    private xyz.michaelsmith.cs4550.project.user.data.entity.User getOrCreateAppUser(User facebookUser, Facebook facebook) {
        xyz.michaelsmith.cs4550.project.user.data.entity.User appUser = userRepository.getByFacebookId(facebookUser.getId());
        if (appUser == null) {
            appUser = new xyz.michaelsmith.cs4550.project.user.data.entity.User();
            appUser.setFacebookId(facebookUser.getId());
            appUser.setName(facebookUser.getName());
            appUser.setProfilePicture(facebook.userOperations().getUserProfileImage(ImageType.LARGE));
            return userRepository.save(appUser);
        }
        return appUser;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(aClass);
    }
}
