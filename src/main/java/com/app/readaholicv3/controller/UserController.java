package com.app.readaholicv3.controller;

import com.app.readaholicv3.dto.BookSummary;
import com.app.readaholicv3.model.User;
import com.app.readaholicv3.recommender.RecommenderService;
import com.app.readaholicv3.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RecommenderService recommenderService;

    @GetMapping("/recommendation/{id}")
    public List<BookSummary> getRecommendation(@PathVariable("id") Long id) {
        User user = userService.findById(id);
        return recommenderService.recommendForUser(user).stream()
                .map(BookSummary::new)
                .collect(Collectors.toList());
    }
}
