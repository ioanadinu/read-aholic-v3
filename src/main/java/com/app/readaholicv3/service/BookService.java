package com.app.readaholicv3.service;

import com.app.readaholicv3.dto.IBookAndRating;
import com.app.readaholicv3.dto.IBookReview;
import com.app.readaholicv3.model.Book;
import com.app.readaholicv3.model.BookRating;
import com.app.readaholicv3.model.BookRatingId;
import com.app.readaholicv3.model.Review;
import com.app.readaholicv3.repository.BookRatingRepository;
import com.app.readaholicv3.repository.BookRepository;
import com.app.readaholicv3.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookRatingRepository bookRatingRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> getBookById(String isbn) {
        return bookRepository.findById(isbn);
    }

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    /** get the 10 best books relative to popularity and average rating */
    public List<IBookAndRating> getTop10() {
        return bookRatingRepository.getTop10();
    }

    public IBookAndRating getBookAndRatingById(String isbn) {
        return bookRatingRepository.findBookAndRatingById(isbn);
    }

    /** intoarce votul pe care un user identificat prin id l-a dat unei carti, indentificate prin isbn */
    public BookRating getBookRatingByUserAndIsbn(String isbn, Long userId) {
        BookRatingId bookRatingId = new BookRatingId(userId, isbn);
        Optional<BookRating> optionalBookRating = bookRatingRepository.findById(bookRatingId);
        return optionalBookRating.orElse(null);
    }

    /** salveaza un vot al unei carti in baza de date, dat de un user indentificat prin id */
    public void saveBookRating(Long userId, String isbn, Integer rating) {
        BookRating bookRating = new BookRating(new BookRatingId(userId, isbn),rating);
        bookRatingRepository.save(bookRating);
    }

    public void saveBookRating(BookRating bookRating) {
        bookRatingRepository.save(bookRating);
    }

    /** return list of items that contain the parameter in the isbn, title or author*/
    public List<IBookAndRating> getSearchResults(String searchParameter) {
        return bookRepository.findSearchResults(searchParameter);
    }

    /** returns the description of a book parsed from the book's page on goodreads */
    public String getBookDescription(String isbn) {
        StringBuffer description = null;
        try {
            URL oracle = new URL("https://www.goodreads.com/search?q="+isbn);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(oracle.openStream()));

            String inputLine;
            Boolean intoDescriptionDiv = false;
            Boolean passedFirstSpan = false;
            while ((inputLine = in.readLine()) != null){
                if(inputLine.contains("id=\"description\"")) intoDescriptionDiv=true;
                if(inputLine.contains("<span") && intoDescriptionDiv && passedFirstSpan) description = new StringBuffer(inputLine);
                if(inputLine.contains("<span") && intoDescriptionDiv) passedFirstSpan=true;
                if(intoDescriptionDiv && inputLine.contains("</div>")) intoDescriptionDiv=false;
            }
            in.close();
            if(description!=null) {
                Integer first = description.indexOf(">");
                description.delete(0, first + 1);
                first = description.indexOf("<br />");
                while (first != -1) {
                    description.delete(first, first + 6);
                    description.insert(first, "\n\n");
                    first = description.indexOf("<br />");
                }
                first = description.indexOf("<i>");
                while(first!=-1) {
                    Integer second = description.indexOf("</i>");
                    description.delete(first,second+4);
                    first = description.indexOf("<i>");
                }

                first = description.indexOf("</span>");
                description.delete(first, first + 7);
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if(description!=null) return description.toString();
        else return "";
    }

    /** intoarce review-ul pe care un user, identificat prin id, l-a lasat unei carti identificate prin isbn */
    public Review getReview(Long userId, String isbn) {
        return reviewRepository.findByUserIdAndIsbn(userId,isbn);
    }

    public void saveReview(Review review) {
        reviewRepository.save(review);
    }

    /** intoarce lista tuturor review-urilor primite de o carte identificata prin isbn */
    public List<IBookReview> getReviewsForBook(String isbn) {
        return reviewRepository.findReviewsForBook(isbn);
    }

    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }
}

