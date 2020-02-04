package com.app.readaholicv3.controller;

import com.app.readaholicv3.dto.*;
import com.app.readaholicv3.model.Review;
import com.app.readaholicv3.repository.ReviewRepository;
import com.app.readaholicv3.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private ReviewRepository reviewRepository;

    @GetMapping("/user/search")
    public CustomPageImpl<UserSummary> searchUsers(
            @RequestParam(value = "searchParam", required = false) String searchParam,
            @RequestParam("page") Integer page) {
        return userService.findUsersByIdOrUsername(searchParam, page);
    }

    @GetMapping("/user/{id}")
    public UserDetailsDto getUser(@PathVariable("id") Long id) {
        return new UserDetailsDto(this.userService.findById(id));
    }

    @PostMapping("user/{id}/activation")
    public StringWrapper activateUser(@PathVariable("id") Long id,
                                      @RequestBody BooleanWrapper booleanWrapper) {
        if(booleanWrapper.aBoolean) {
            userService.enableUserById(id);
        }
        else userService.disableUserById(id);
        return new StringWrapper("success");
    }

    @GetMapping("user/{id}/reviews")
    public List<Review> getReviewsForUser(@PathVariable("id") Long id) {
        return this.reviewRepository.findByUserId(id);
    }

    @PostMapping("user/deleteReview")
    public StringWrapper activateUser(@RequestBody LongWrapper longWrapper) {
        this.reviewRepository.deleteById(longWrapper.aLong);
        return new StringWrapper("success");
    }

    @PostMapping("/user/make-admin")
    public StringWrapper makeUserAdmin(@RequestBody LongWrapper longWrapper) {
        this.userService.makeAdmin(longWrapper.aLong);
        return new StringWrapper("success");
    }
}
