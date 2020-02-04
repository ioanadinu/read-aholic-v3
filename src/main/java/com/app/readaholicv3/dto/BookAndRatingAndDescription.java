package com.app.readaholicv3.dto;

public class BookAndRatingAndDescription {

    private String isbn;

    private String title;

    private String author;

    private Integer yearOfPublication;

    private String publisher;

    private String imageUrlS;

    private String imageUrlM;

    private String imageUrlL;

    private Float rating;

    private String description;

    public BookAndRatingAndDescription() {
    }

    public BookAndRatingAndDescription(IBookAndRating iBookAndRating, String description) {
        this.isbn = iBookAndRating.getIsbn();
        this.title = iBookAndRating.getBook_Title();
        this.author = iBookAndRating.getBook_Author();
        this.yearOfPublication = iBookAndRating.getYear_Of_Publication();
        this.publisher = iBookAndRating.getPublisher();
        this.imageUrlS = iBookAndRating.getImage_Url_S();
        this.imageUrlM = iBookAndRating.getImage_Url_M();
        this.imageUrlL = iBookAndRating.getImage_Url_L();
        this.rating = iBookAndRating.getAverage();
        this.description = description;
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

    public Integer getYearOfPublication() {
        return yearOfPublication;
    }

    public String getPublisher() {
        return publisher;
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

    public String getDescription() {
        return description;
    }
}
