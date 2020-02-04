package com.app.readaholicv3.repository;

import com.app.readaholicv3.dto.IBookReview;
import com.app.readaholicv3.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review,Long> {

    List<Review> findByUserId(Long userId);

    /** intoarce lista tuturor review-urilor primite de o carte */
    @Query(value = "SELECT review_id, users.user_id, username, text\n" +
            "FROM public.reviews, public.users\n" +
            "WHERE reviews.user_id = users.user_id AND book_id = ?1\n" +
            "ORDER BY review_id DESC;",
            nativeQuery = true)
    List<IBookReview> findReviewsForBook(String isbn);
}