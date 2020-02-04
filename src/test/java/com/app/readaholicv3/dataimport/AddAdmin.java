package com.app.readaholicv3.dataimport;

import com.app.readaholicv3.model.Role;
import com.app.readaholicv3.model.RoleName;
import com.app.readaholicv3.model.User;
import com.app.readaholicv3.repository.RoleRepository;
import com.app.readaholicv3.repository.UserRepository;
import com.app.readaholicv3.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest
@ComponentScan(value = "com.maximaLibri.maximaLibriV2")
@EnableTransactionManagement
public class AddAdmin {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

//    @Test
//    @Transactional
//    @Rollback(false)
    public void addRoles() {
        roleRepository.save(new Role(1L, RoleName.ROLE_USER));
        roleRepository.save(new Role(2L, RoleName.ROLE_ADMIN));
    }

//    @Test
//    @Transactional
//    @Rollback(false)
    public void addAdmin() {
        User admin = userService.findByEmail("ioana.dinu196@gmail.com");
        if(admin==null) {
            admin = new User();
            admin.setUsername("SuperAdmin");
            admin.setEmail("ioana.dinu196@gmail.com");
            admin.setPassword(userService.encodePassword("secret"));
            admin.setActive(true);
            admin.setRoles(Arrays.asList(roleRepository.findByName(RoleName.ROLE_USER), roleRepository.findByName(RoleName.ROLE_ADMIN)));
            userRepository.save(admin);
        }
    }
}
