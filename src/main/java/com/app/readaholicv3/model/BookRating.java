package com.app.readaholicv3.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "bx_book_ratings")
public class BookRating {

    @EmbeddedId
    private BookRatingId bookRatingId;

    @Column(name = "book_rating")
    private Integer bookRating;

    public BookRating() {
    }

    public BookRating(BookRatingId bookRatingId, Integer bookRating) {
        this.bookRatingId = bookRatingId;
        this.bookRating = bookRating;
    }

    public BookRatingId getBookRatingId() {
        return bookRatingId;
    }

    public Integer getBookRating() {
        return bookRating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookRating that = (BookRating) o;
        return bookRatingId.equals(that.bookRatingId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookRatingId);
    }

    @Override
    public String toString() {
        return "BookRating{" +
                "bookRatingId=" + bookRatingId +
                ", bookRating=" + bookRating +
                '}';
    }
}
