package com.app.readaholicv3;

import com.app.readaholicv3.model.RoleName;
import com.app.readaholicv3.model.User;
import com.app.readaholicv3.repository.RoleRepository;
import com.app.readaholicv3.repository.UserRepository;
import com.app.readaholicv3.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;

//@Component
public class AddAdmin {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

//    @EventListener(ApplicationReadyEvent.class)
    public void addAdmin() {
        User admin = userService.findByEmail("alexa.murgoci@gmail.com");
        if(admin==null) {
            admin = new User();
            admin.setUsername("SuperAdmin");
            admin.setEmail("alexa.murgoci@gmail.com");
            admin.setPassword(userService.encodePassword("secret"));
            admin.setActive(true);
            admin.setRoles(Arrays.asList(roleRepository.findByName(RoleName.ROLE_USER), roleRepository.findByName(RoleName.ROLE_ADMIN)));
            userRepository.save(admin);
        }
    }
}
