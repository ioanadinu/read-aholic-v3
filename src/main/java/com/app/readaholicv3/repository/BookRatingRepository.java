package com.app.readaholicv3.repository;

import com.app.readaholicv3.dto.IBookAndRating;
import com.app.readaholicv3.dto.IUserHistoryItem;
import com.app.readaholicv3.model.BookRating;
import com.app.readaholicv3.model.BookRatingId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRatingRepository extends JpaRepository<BookRating, BookRatingId> {

    BookRating findByBookRatingIdUserIdAndBookRatingIdIsbn(Long userId, String isbn);

    /** intoarce istoricul voturilor unui user identificat prin id */
    @Query(value = "SELECT * FROM bx_book_ratings WHERE user_id=?1",
            nativeQuery = true)
    List<BookRating> findRatingsByUserId(Long userId);

    @Query(value = "SELECT AVG(book_rating) FROM bx_book_ratings WHERE isbn=?1",
            nativeQuery=true)
    Float getBookRating(String isbn);

    /** get the 10 best books relative to popularity and average rating */
    @Query(value = "SELECT bx_books.isbn, book_title, book_author, year_of_publication, publisher, image_url_s, image_url_m, image_url_l, average\n" +
            "FROM bx_books, \n" +
            "(SELECT isbn, ROUND((SUM(book_rating)+1000*((SELECT AVG(book_rating) as avgall from public.bx_book_ratings)))/(1000+COUNT(book_rating)),2) as average\n"+
            "FROM bx_book_ratings\n" +
            "GROUP BY isbn) as selection\n" +
            "WHERE bx_books.isbn=selection.isbn\n" +
            "ORDER BY average DESC\n" +
            "LIMIT 12;",
            nativeQuery = true)
    List<IBookAndRating> getTop12();

    @Query(value = "SELECT bx_books.isbn, book_title, book_author, year_of_publication, publisher, image_url_s, image_url_m, image_url_l, average\n" +
            "FROM public.bx_books, \n" +
            "\t(SELECT isbn,ROUND(AVG(book_rating),2) as average\n" +
            "\tFROM public.bx_book_ratings\n" +
            "\tGROUP BY isbn) as book_rating_avg\n" +
            "WHERE bx_books.isbn = book_rating_avg.isbn AND bx_books.isbn = ?1 ",
            nativeQuery = true)
    IBookAndRating findBookAndRatingById(String isbn);
}
