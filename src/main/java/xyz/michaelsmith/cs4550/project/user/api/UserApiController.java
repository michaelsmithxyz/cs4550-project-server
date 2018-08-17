package xyz.michaelsmith.cs4550.project.user.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.michaelsmith.cs4550.project.common.api.exception.ResourceNotFoundException;
import xyz.michaelsmith.cs4550.project.user.service.UserService;

import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/api/user")
public class UserApiController {
    private final UserService userService;

    @Autowired
    public UserApiController(UserService userService) {
        this.userService = userService;
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
