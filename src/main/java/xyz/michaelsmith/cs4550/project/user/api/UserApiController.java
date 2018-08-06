package xyz.michaelsmith.cs4550.project.user.api;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/me")
public class UserApiController {
    @RequestMapping(path = {"", "/"},
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public Authentication getUserInfo() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
