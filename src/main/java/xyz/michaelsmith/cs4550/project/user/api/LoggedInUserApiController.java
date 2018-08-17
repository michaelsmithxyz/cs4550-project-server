package xyz.michaelsmith.cs4550.project.user.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import xyz.michaelsmith.cs4550.project.common.dto.mapper.DtoMapper;
import xyz.michaelsmith.cs4550.project.user.data.entity.User;
import xyz.michaelsmith.cs4550.project.user.dto.UserDto;

@RestController
@RequestMapping("/api/me")
public class LoggedInUserApiController {
    private final DtoMapper<User, UserDto> userDtoMapper;

    @Autowired
    public LoggedInUserApiController(DtoMapper<User, UserDto> userDtoMapper) {
        this.userDtoMapper = userDtoMapper;
    }

    @RequestMapping(path = {"", "/"},
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDto getLoggedInUserInfo() {
        return userDtoMapper.map((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }
}
