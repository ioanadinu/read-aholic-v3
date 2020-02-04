package com.app.readaholicv3.config;

import com.app.readaholicv3.model.RoleName;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

class UrlRoles {
    String url;
    Set<RoleName> roles;

    UrlRoles(String url, List<RoleName> roles) {
        this.url = url;
        this.roles = new HashSet<>(roles);
    }
}
