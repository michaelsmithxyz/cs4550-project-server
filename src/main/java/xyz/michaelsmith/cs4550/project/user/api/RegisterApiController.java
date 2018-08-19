package xyz.michaelsmith.cs4550.project.user.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.michaelsmith.cs4550.project.user.dto.UserDto;
import xyz.michaelsmith.cs4550.project.user.dto.UserRegistrationDto;
import xyz.michaelsmith.cs4550.project.user.service.UserService;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/register")
public class RegisterApiController {
    private final UserService userService;

    @Autowired
    public RegisterApiController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(path = {"", "/"},
                    method = POST,
                    consumes = APPLICATION_JSON_VALUE,
                    produces = APPLICATION_JSON_VALUE)
    public UserDto registerUser(@RequestBody UserRegistrationDto registrationDto) {
        return userService.registerUser(registrationDto);
    }
}
