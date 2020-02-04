package com.app.readaholicv3.dto;

import com.app.readaholicv3.model.Role;
import com.app.readaholicv3.model.RoleName;
import com.app.readaholicv3.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserSummary {
    public Long id;
    public String username;
    public String email;
    public List<RoleName> roles;
    public Boolean active;

    public UserSummary() {
    }

    public UserSummary(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.roles = user.getRoles().stream().map(Role::getName).collect(Collectors.toList());
        this.active = user.isActive();
    }
}
