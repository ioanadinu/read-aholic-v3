package com.app.readaholicv3.config;

import com.app.readaholicv3.model.RoleName;

import java.util.List;

import static java.util.Arrays.asList;

class SecurityURLs {
    final static List<UrlRoles> URLS = asList(
            new UrlRoles("/admin/(.*)", asList(RoleName.ROLE_USER, RoleName.ROLE_ADMIN))
    ) ;

    private SecurityURLs() {
    }
}
