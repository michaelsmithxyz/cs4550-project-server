package xyz.michaelsmith.cs4550.project.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.stereotype.Component;
import xyz.michaelsmith.cs4550.project.user.data.UserRepository;
import xyz.michaelsmith.cs4550.project.user.data.entity.User;

import java.util.Map;

@Component
public class FacebookPrincipalExtractor implements PrincipalExtractor {
    private final UserRepository userRepository;

    @Autowired
    public FacebookPrincipalExtractor(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Object extractPrincipal(Map<String, Object> map) {
        Long facebookId = Long.valueOf((String) map.get("id"));
        User existingUser = userRepository.getByFacebookId(facebookId);
        if (existingUser == null) {
            User newUser = new User();
            newUser.setName((String) map.get("name"));
            newUser.setFacebookId(facebookId);

            Map<String, Map<String, String>> pictureDataMap = (Map<String, Map<String, String>>) map.get("picture");
            newUser.setProfilePictureUrl(pictureDataMap.get("data").get("url"));

            return userRepository.save(newUser);
        }
        return existingUser;
    }
}
