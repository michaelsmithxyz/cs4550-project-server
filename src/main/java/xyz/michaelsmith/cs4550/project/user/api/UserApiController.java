package xyz.michaelsmith.cs4550.project.user.api;

import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import xyz.michaelsmith.cs4550.project.user.data.entity.User;

@RestController
@RequestMapping("/api/me")
public class UserApiController {
    @RequestMapping(path = {"", "/"},
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUserInfo() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
