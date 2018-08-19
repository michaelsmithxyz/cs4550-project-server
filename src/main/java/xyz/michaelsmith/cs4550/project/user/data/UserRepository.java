package xyz.michaelsmith.cs4550.project.user.data;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.michaelsmith.cs4550.project.user.data.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User getByFacebookId(String facebookId);

    Optional<User> findByEmail(String email);
}
