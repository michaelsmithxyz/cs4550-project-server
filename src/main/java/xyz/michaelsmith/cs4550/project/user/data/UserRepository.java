package xyz.michaelsmith.cs4550.project.user.data;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.michaelsmith.cs4550.project.user.data.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User getByFacebookId(Long facebookId);
}
