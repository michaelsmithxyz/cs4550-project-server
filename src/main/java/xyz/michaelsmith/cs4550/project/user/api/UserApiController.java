package xyz.michaelsmith.cs4550.project.user.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.michaelsmith.cs4550.project.common.api.exception.ResourceNotFoundException;
import xyz.michaelsmith.cs4550.project.user.dto.UserDto;
import xyz.michaelsmith.cs4550.project.user.dto.UserRoleDto;
import xyz.michaelsmith.cs4550.project.user.service.UserService;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@RestController
@RequestMapping("/api/user")
public class UserApiController {
    private final UserService userService;

    @Autowired
    public UserApiController(UserService userService) {
        this.userService = userService;
    }


    // Logged-in User Operations

    @RequestMapping(path = "/me",
                    method = GET,
                    produces = APPLICATION_JSON_VALUE)
    public UserDto getLoggedInUserInfo() {
        return userService.getUser();
    }

    @RequestMapping(path = "/me/role",
                    method = PUT,
                    consumes = APPLICATION_JSON_VALUE)
    public void updateUserRole(@RequestBody UserRoleDto role) {
        userService.updateUserRole(role.getRole());
    }

    @RequestMapping(path = "/me/following/{userId}",
                    method = PUT)
    public void followUser(@PathVariable("userId") Long userId) {
        try {
            UserDto loggedInUser = userService.getUser();
            userService.createFollowMapping(loggedInUser.getId(), userId);
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException(ex.getMessage());
        }
    }

    @RequestMapping(path = "/me/following/{userId}",
                    method = DELETE)
    public void unfollowUser(@PathVariable("userId") Long userId) {
        try {
            UserDto loggedInUser = userService.getUser();
            userService.deleteFollowMapping(loggedInUser.getId(), userId);
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException(ex.getMessage());
        }
    }

    // General User operations

    @RequestMapping(path = "/{userId}",
                    method = GET,
                    produces = APPLICATION_JSON_VALUE)
    public UserDto getUserById(@PathVariable("userId") Long userId) {
        try {
            return userService.getUser(userId);
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException(ex.getMessage());
        }
    }

    @RequestMapping(path = "/{userId}/followers",
                    method = GET,
                    produces = APPLICATION_JSON_VALUE)
    public List<UserDto> getFollowersForUser(@PathVariable("userId") Long userId) {
        try {
            return userService.getFollowersForUser(userId);
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException(ex.getMessage());
        }
    }

    @RequestMapping(path = "/{userId}/following",
                    method = GET,
                    produces = APPLICATION_JSON_VALUE)
    public List<UserDto> getFollowingForUser(@PathVariable("userId") Long userId) {
        try {
            return userService.getFollowingForUser(userId);
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException(ex.getMessage());
        }
    }

    @RequestMapping(path = "/{userId}/picture.jpg",
                    method = GET,
                    produces = IMAGE_JPEG_VALUE)
    public byte[] getUserProfilePicture(@PathVariable("userId") Long userId) {
        byte[] picture = userService.getUserProfilePicture(userId);
        if (picture == null) {
            throw new ResourceNotFoundException("No user found");
        }
        return picture;
    }
}
