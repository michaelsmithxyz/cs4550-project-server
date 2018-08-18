package xyz.michaelsmith.cs4550.project.user.dto;

import xyz.michaelsmith.cs4550.project.user.data.entity.UserRole;

public class UserRoleDto {
    private UserRole role;

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
