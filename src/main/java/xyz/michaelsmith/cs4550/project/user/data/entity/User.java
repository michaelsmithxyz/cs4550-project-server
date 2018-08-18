package xyz.michaelsmith.cs4550.project.user.data.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "app_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String facebookId;

    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.DEFAULT;

    private String name;
    @Lob
    private byte[] profilePicture;

    @ManyToMany
    @JoinTable(name = "user_follower_map",
               joinColumns = @JoinColumn(name = "target_id"),
               inverseJoinColumns = @JoinColumn(name = "follower_id"))
    private List<User> followedBy;

    @ManyToMany
    @JoinTable(name = "user_follower_map",
               joinColumns = @JoinColumn(name = "follower_id"),
               inverseJoinColumns = @JoinColumn(name = "target_id"))
    private List<User> following;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public List<User> getFollowedBy() {
        return followedBy;
    }

    public void setFollowedBy(List<User> followedBy) {
        this.followedBy = followedBy;
    }

    public List<User> getFollowing() {
        return following;
    }

    public void setFollowing(List<User> following) {
        this.following = following;
    }
}
