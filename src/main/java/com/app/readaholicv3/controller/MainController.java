package com.app.readaholicv3.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class MainController {

    @GetMapping("/")
    public ResponseEntity<String> getMinimal() {
        System.out.println("MINIMAL: GETMINIMAL()");

        return new ResponseEntity<String>("returnstring", HttpStatus.OK);
    }

    @PostMapping("/post")
    public ResponseEntity<String> getMinimalPost(@RequestBody String ceva) {
        return new ResponseEntity<String>(ceva, HttpStatus.OK);
    }
}
