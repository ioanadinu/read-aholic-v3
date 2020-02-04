package com.app.readaholicv3.dto;

import com.app.readaholicv3.model.RoleName;

import java.util.HashSet;
import java.util.Set;

public class UserIdAndRoles {
    public Long userId;
    public Set<RoleName> roles;

    public UserIdAndRoles() {
        userId = 0l;
        roles = new HashSet<>();
    }

    public UserIdAndRoles(Long userId, Set<RoleName> roles) {
        this.userId = userId;
        this.roles = roles;
    }
}
