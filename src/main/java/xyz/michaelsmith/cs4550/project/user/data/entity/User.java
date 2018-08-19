package xyz.michaelsmith.cs4550.project.user.data.entity;

import xyz.michaelsmith.cs4550.project.recipe.data.entity.Recipe;

import javax.persistence.*;
import java.util.Date;
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
    private String email;
    private String password;

    @Lob
    private byte[] profilePicture;

    private Date joined;

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

    @OneToMany(mappedBy = "author", cascade = CascadeType.REMOVE)
    private List<Recipe> recipes;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getJoined() {
        return joined;
    }

    public void setJoined(Date joined) {
        this.joined = joined;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }
}
