package com.app.readaholicv3.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "bx_books")
public class Book {

    @Id
    @Column
    private String isbn;

    @Column(name = "book_title")
    private String title;

    @Column(name = "book_author")
    private String author;

    @Column(name = "year_of_publication")
    private Integer yearOfPublication;

    @Column
    private String publisher;

    @Column(name = "image_url_s")
    private String imageUrlS;

    @Column(name = "image_url_m")
    private String imageUrlM;

    @Column(name = "image_url_l")
    private String imageUrlL;

    /*@Column
    private String description;

    @Column
    private String keywords;*/

    public Book() {
    }

    public Book(String isbn, String title, String author, Integer yearOfPublication, String publisher, String imageUrlS, String imageUrlM, String imageUrlL) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.yearOfPublication = yearOfPublication;
        this.publisher = publisher;
        this.imageUrlS = imageUrlS;
        this.imageUrlM = imageUrlM;
        this.imageUrlL = imageUrlL;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return isbn.equals(book.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }

    @Override
    public String toString() {
        return "Book{" +
                "isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", yearOfPublication=" + yearOfPublication +
                ", publisher='" + publisher + '\'' +
                ", imageUrlS='" + imageUrlS + '\'' +
                ", imageUrlM='" + imageUrlM + '\'' +
                ", imageUrlL='" + imageUrlL + '\'' +
                '}';
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
}
