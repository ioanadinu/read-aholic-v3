package com.app.readaholicv3.config;

import com.app.readaholicv3.model.RoleName;

import java.util.List;

import static java.util.Arrays.asList;

class SecurityURLs {
    final static List<UrlRoles> URLS = asList(
            new UrlRoles("/admin/(.*)", asList(RoleName.ROLE_USER, RoleName.ROLE_ADMIN)),
            new UrlRoles("/user/(.*)", asList(RoleName.ROLE_USER)),
            new UrlRoles("/book/rate(.*)", asList(RoleName.ROLE_USER)),
            new UrlRoles("/book/un-rate(.*)", asList(RoleName.ROLE_USER)),
            new UrlRoles("/book/review(.*)", asList(RoleName.ROLE_USER))
    );

    private SecurityURLs() {
    }
}
