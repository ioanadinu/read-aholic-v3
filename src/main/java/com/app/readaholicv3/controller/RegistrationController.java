package com.app.readaholicv3.controller;

import com.app.readaholicv3.dto.StringWrapper;
import com.app.readaholicv3.dto.UserRegistrationDto;
import com.app.readaholicv3.model.User;
import com.app.readaholicv3.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("/register")
@CrossOrigin(origins = "http://localhost:4200")
public class RegistrationController {
    private static final Logger LOGGER = Logger.getLogger(RegistrationController.class.getName());

    @Autowired
    private UserService userService;

    @PostMapping()
    public ResponseEntity<StringWrapper> register(@RequestBody UserRegistrationDto userRegistrationDto) {
        User existing = userService.findByEmail(userRegistrationDto.getEmail());
        if(existing!=null) {
            return new ResponseEntity<>(new StringWrapper("There is already an account registered with that email"), HttpStatus.NOT_ACCEPTABLE);
        }
        existing = userService.getUserByUsername(userRegistrationDto.getUsername());
        if(existing!=null) {
            return new ResponseEntity<>(new StringWrapper("There is already an account registered with that username"), HttpStatus.NOT_ACCEPTABLE);
        }

        userService.save(userRegistrationDto);

        return new ResponseEntity<>(new StringWrapper("success"), HttpStatus.CREATED);
    }

}
