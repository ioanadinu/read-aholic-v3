package com.app.readaholicv3.service;

import com.app.readaholicv3.dto.*;
import com.app.readaholicv3.model.Book;
import com.app.readaholicv3.model.BookRating;
import com.app.readaholicv3.model.BookRatingId;
import com.app.readaholicv3.model.Review;
import com.app.readaholicv3.repository.BookRatingRepository;
import com.app.readaholicv3.repository.BookRepository;
import com.app.readaholicv3.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

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
    public List<IBookAndRating> getTop12() {
        return bookRatingRepository.getTop12();
    }

    public BookRating getBookRatingOfUser(Long userId, String isbn) {
        return this.bookRatingRepository.findByBookRatingIdUserIdAndBookRatingIdIsbn(userId, isbn);
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

    public void deleteBookRating(Long userId, String isbn) {
        bookRatingRepository.deleteById(new BookRatingId(userId, isbn));
    }

    /** return list of items that contain the parameter in the isbn, title or author*/
    @Transactional
    public CustomPageImpl<BookSummary> getSearchResults(String searchParameter, Integer page) {
        Pageable pageRequest = PageRequest.of(page, 10, Sort.by("title"));
        Page<Book> books = searchParameter!= null ?
                bookRepository.findAllByIsbnContainingIgnoreCaseOrTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(searchParameter, searchParameter, searchParameter, pageRequest) :
                bookRepository.findAll(pageRequest);
        return new CustomPageImpl<>(books.stream()
                .map(b -> {
                    Float rating = bookRatingRepository.getBookRating(b.getIsbn());
                    return new BookSummary(
                            b.getIsbn(),
                            b.getTitle(),
                            b.getAuthor(),
                            b.getImageUrlS(),
                            b.getImageUrlM(),
                            b.getImageUrlL(),
                            rating == null ? 0f : rating / 2);
                })
                .collect(toList()),
                books.getTotalPages(), books.getTotalElements());
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

    public void saveReview(Review review) {
        reviewRepository.save(review);
    }

    /** intoarce lista tuturor review-urilor primite de o carte identificata prin isbn */
    @Transactional
    public List<BookReview> getReviewsForBook(final String isbn) {
        List<IBookReview> iBookReviewList = reviewRepository.findReviewsForBook(isbn);
        return iBookReviewList.stream()
                .map(r -> {
                    BookRating rating = this.bookRatingRepository.findByBookRatingIdUserIdAndBookRatingIdIsbn(r.getUser_Id(), isbn);
                    Float ratingF = rating==null ? 0f : rating.getBookRating()/2f;
                    return new BookReview(r.getUsername(), ratingF, r.getText());
                })
                .collect(toList());
    }
}

