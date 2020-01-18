package com.app.readaholicv3.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class BookRatingId implements Serializable {

    @Column(name = "user_id")
    private Long userId;

    @Column
    private String isbn;

    public BookRatingId() {
    }

    public BookRatingId(Long userId, String isbn) {
        this.userId = userId;
        this.isbn = isbn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookRatingId that = (BookRatingId) o;
        return userId.equals(that.userId) &&
                isbn.equals(that.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, isbn);
    }

    public Long getUserId() {
        return userId;
    }

    public String getIsbn() {
        return isbn;
    }
}
