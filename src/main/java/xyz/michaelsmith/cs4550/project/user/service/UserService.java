package xyz.michaelsmith.cs4550.project.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import xyz.michaelsmith.cs4550.project.common.dto.mapper.DtoMapper;
import xyz.michaelsmith.cs4550.project.config.security.auth.DatabaseUserDetails;
import xyz.michaelsmith.cs4550.project.config.security.util.AuthenticationUtils;
import xyz.michaelsmith.cs4550.project.user.data.UserRepository;
import xyz.michaelsmith.cs4550.project.user.data.entity.User;
import xyz.michaelsmith.cs4550.project.user.data.entity.UserRole;
import xyz.michaelsmith.cs4550.project.user.dto.UserDto;
import xyz.michaelsmith.cs4550.project.user.dto.UserRegistrationDto;

import java.util.Date;
import java.util.List;
import java.util.function.Supplier;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;

@Service
public class UserService {
    private final DtoMapper<User, UserDto> userDtoMapper;
    private final UserRepository userRepository;
    private final AuthenticationUtils authenticationUtils;

    private static final Supplier<? extends RuntimeException> USER_NOT_FOUND_EXCEPTION = () -> new IllegalArgumentException("No user found for ID");

    @Autowired
    public UserService(DtoMapper<User, UserDto> userDtoMapper, UserRepository userRepository, AuthenticationUtils authenticationUtils) {
        this.userDtoMapper = userDtoMapper;
        this.userRepository = userRepository;
        this.authenticationUtils = authenticationUtils;
    }

    public UserDto getUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof DatabaseUserDetails) {
            return userDtoMapper.map(((DatabaseUserDetails) principal).getUser());
        }
        return userDtoMapper.map((User) principal);
    }

    public UserDto registerUser(UserRegistrationDto registrationDto) {
        if (registrationDto.getEmail() == null || registrationDto.getPassword() == null) {
            throw new IllegalArgumentException("Missing required fields");
        }
        if (userRepository.findByEmail(registrationDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("User exists with email");
        }
        User user = new User();
        user.setName(registrationDto.getName());
        user.setEmail(registrationDto.getEmail());
        user.setRole(registrationDto.getRole());
        user.setPassword(registrationDto.getPassword());
        user.setJoined(new Date());

        user = userRepository.save(user);
        authenticationUtils.authUser(user);
        return userDtoMapper.map(user);
    }

    public void updateUserRole(UserRole role) {
        User user = userRepository.findById(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()).orElseThrow(USER_NOT_FOUND_EXCEPTION);
        user.setRole(role);
        user = userRepository.save(user);
        Authentication auth = new UsernamePasswordAuthenticationToken(user, SecurityContextHolder.getContext().getAuthentication().getCredentials(), singletonList(new SimpleGrantedAuthority(user.getRole().name())));
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    public User getUserEntity() {
        return userRepository.findById(this.getUser().getId()).orElseThrow(USER_NOT_FOUND_EXCEPTION);
    }

    public UserDto getUser(Long userId) {
        return userRepository.findById(userId).map(userDtoMapper::map).orElseThrow(USER_NOT_FOUND_EXCEPTION);
    }

    public List<UserDto> getFollowersForUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(USER_NOT_FOUND_EXCEPTION);
        return user.getFollowedBy().stream().map(userDtoMapper::map).collect(toList());
    }

    public List<UserDto> getFollowingForUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(USER_NOT_FOUND_EXCEPTION);
        return user.getFollowing().stream().map(userDtoMapper::map).collect(toList());
    }

    public void createFollowMapping(Long followingUserId, Long targetUserId) {
        User followingUser = userRepository.findById(followingUserId).orElseThrow(USER_NOT_FOUND_EXCEPTION);
        User targetUser = userRepository.findById(targetUserId).orElseThrow(USER_NOT_FOUND_EXCEPTION);

        followingUser.getFollowing().add(targetUser);
        userRepository.save(followingUser);
    }

    public void deleteFollowMapping(Long followingUserId, Long targetUserId) {
        User followingUser = userRepository.findById(followingUserId).orElseThrow(USER_NOT_FOUND_EXCEPTION);
        followingUser.getFollowing().removeIf(u -> u.getId().equals(targetUserId));
        userRepository.save(followingUser);
    }

    public byte[] getUserProfilePicture(Long userId) {
        return userRepository.findById(userId).map(User::getProfilePicture).orElse(null);
    }
}
