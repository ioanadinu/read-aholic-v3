package com.app.readaholicv3.dto;

import com.app.readaholicv3.model.RoleName;
import com.app.readaholicv3.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserDetailsDto {
    public Long id;
    public String username;
    public String email;
    public Boolean active;
    public List<RoleName> roles;

    public UserDetailsDto(User user) {
        id = user.getId();
        username = user.getUsername();
        email = user.getEmail();
        active = user.isActive();
        roles = user.getRoles().stream()
            .map(r -> (r.getName()))
            .collect(Collectors.toList());
    }

    public UserDetailsDto() {
        roles = new ArrayList<>();
    }
}
