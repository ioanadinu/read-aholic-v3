package com.app.readaholicv3.dto;

import java.io.Serializable;

public class BookSummary implements Serializable {
    private String isbn;

    private String title;

    private String author;

    private String imageUrlS;

    private String imageUrlM;

    private String imageUrlL;

    private Float rating;

    public BookSummary() {
    }

    public BookSummary(String isbn, String title, String author, String imageUrlS, String imageUrlM, String imageUrlL, Float rating) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.imageUrlS = imageUrlS;
        this.imageUrlM = imageUrlM;
        this.imageUrlL = imageUrlL;
        this.rating = rating/2;
    }

    public BookSummary(IBookAndRating iBookAndRating) {
        if(iBookAndRating != null) {
            this.isbn = iBookAndRating.getIsbn();
            this.title = iBookAndRating.getBook_Title();
            this.author = iBookAndRating.getBook_Author();
            this.imageUrlS = iBookAndRating.getImage_Url_S();
            this.imageUrlM = iBookAndRating.getImage_Url_M();
            this.imageUrlL = iBookAndRating.getImage_Url_L();
            this.rating = iBookAndRating.getAverage() == null ? 0f : iBookAndRating.getAverage() / 2;
        }
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getImageUrlS() {
        return imageUrlS;
    }

    public String getImageUrlM() {
        return imageUrlM;
    }

    public String getImageUrlL() {
        return imageUrlL;
    }

    public Float getRating() {
        return rating;
    }
}
