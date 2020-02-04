package com.app.readaholicv3.config;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.app.readaholicv3.model.Role;
import com.app.readaholicv3.model.RoleName;
import com.app.readaholicv3.model.User;
import com.app.readaholicv3.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Component
public class SecurityHttpInterceptor extends HandlerInterceptorAdapter {
    private static final Logger LOGGER = Logger.getLogger(SecurityHttpInterceptor.class.getName());
    private static final String USER_EMAIL_HEADER = "user-email";
    private static final String USER_PASSWORD_HEADER = "user-password";

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest requestServlet, HttpServletResponse responseServlet, Object handler) throws Exception
    {
        String url = requestServlet.getRequestURI();
        LOGGER.info(url);

        Set<RoleName> roles = getRequiredRolesForUrl(url);
        List<String> headers = Collections.list(requestServlet.getHeaderNames());
        if(roles.isEmpty())
            return true;

        if(!headers.contains(USER_EMAIL_HEADER) || !headers.contains((USER_PASSWORD_HEADER))) {
            responseServlet.setStatus(HttpStatus.FORBIDDEN.value());
            return false;
        }

        User user = userService.findByEmail(requestServlet.getHeader(USER_EMAIL_HEADER));

        if(user==null || !BCrypt.verifyer().verify(requestServlet.getHeader(USER_PASSWORD_HEADER).toCharArray(), user.getPassword()).verified) {
            responseServlet.setStatus(HttpStatus.FORBIDDEN.value());
            return false;
        }

        if(!user.getRoles().stream().map(Role::getName).collect(Collectors.toSet()).containsAll(roles)) {
            responseServlet.setStatus(HttpStatus.FORBIDDEN.value());
            return false;
        }

        return true;
    }

    private Set<RoleName> getRequiredRolesForUrl(String url) {
        for(UrlRoles urlRoles : SecurityURLs.URLS) {
            if(url.matches(urlRoles.url)) return urlRoles.roles;
        }
        return Collections.emptySet();
    }
}
