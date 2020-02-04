package com.app.readaholicv3.controller;

import com.app.readaholicv3.dto.UserDetails;
import com.app.readaholicv3.model.BookRating;
import com.app.readaholicv3.model.BookRatingId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class MainController {

    @GetMapping("/")
    public BookRating getMinimal() {
        System.out.println("MINIMAL: GETMINIMAL()");

        return new BookRating(new BookRatingId(101L,"isbn"),10);
    }

    @PostMapping("/post")
    public UserDetails getMinimalPost(@RequestBody String ceva) {
        System.out.println("getMinimalPost: "+ceva);
        return new UserDetails(ceva);
    }

    @GetMapping("/admin/{id}")
    public UserDetails adminMethod(@PathVariable("id") String id) {
        System.out.println("admin "+id);
        return new UserDetails("data for " + id + " is accessible");
    }
}
