package com.app.readaholicv3.dto;

public class BookReview {
    public String username;
    public Float userRating;
    public String userReview;

    public BookReview() {
    }

    public BookReview(String username, Float userRating, String userReview) {
        this.username = username;
        this.userRating = userRating;
        this.userReview = userReview;
    }
}
