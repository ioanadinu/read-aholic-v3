package com.app.readaholicv3.controller;

import com.app.readaholicv3.dto.UserIdAndRoles;
import com.app.readaholicv3.dto.UserLoginDto;
import com.app.readaholicv3.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class MainController {
    private static final Logger LOGGER = Logger.getLogger(MainController.class.getName());

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<UserIdAndRoles> loginAndReturnRoles(@RequestBody UserLoginDto userLoginDto) {
        try {
            return new ResponseEntity<>(userService.verifyLogin(userLoginDto), HttpStatus.OK);
        }
        catch (RuntimeException e) {
            return new ResponseEntity<>(new UserIdAndRoles(), HttpStatus.NOT_FOUND);
        }
    }
}
