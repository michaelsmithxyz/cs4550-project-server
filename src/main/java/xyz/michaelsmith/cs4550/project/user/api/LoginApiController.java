package xyz.michaelsmith.cs4550.project.user.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import xyz.michaelsmith.cs4550.project.user.data.entity.User;
import xyz.michaelsmith.cs4550.project.user.dto.LoginRequestDto;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/login")
public class LoginApiController {
    private final AuthenticationProvider authenticationProvider;

    @Autowired
    public LoginApiController(AuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
    }

    @RequestMapping(path = {"", "/"},
                    method = RequestMethod.POST,
                    consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public User login(HttpServletRequest request, @RequestBody LoginRequestDto loginRequest) {
        Authentication authRequest = new UsernamePasswordAuthenticationToken(loginRequest.getFacebookId(), loginRequest.getFacebookToken());
        Authentication authentication = authenticationProvider.authenticate(authRequest);
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(authentication);
        request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, context);
        return (User) authentication.getPrincipal();
    }
}
