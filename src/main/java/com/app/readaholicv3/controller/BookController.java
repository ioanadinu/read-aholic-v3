package com.app.readaholicv3.controller;

import com.app.readaholicv3.dto.*;
import com.app.readaholicv3.model.BookRating;
import com.app.readaholicv3.model.Review;
import com.app.readaholicv3.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/book")
@CrossOrigin(origins = "http://localhost:4200")
public class BookController {

    @Autowired
    BookService bookService;

    @GetMapping("/top")
    public List<BookSummary> getTop12Books() {
        return bookService.getTop12().stream().map(BookSummary::new).collect(toList());
    }

    @GetMapping("/search")
    public CustomPageImpl<BookSummary> search(
            @RequestParam(value = "searchParam", required = false) String searchParam,
            @RequestParam("page") Integer page
    ) {
        return bookService.getSearchResults(searchParam, page);
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<BookAndRatingAndDescription> getBook(@PathVariable("isbn") String isbn) {
        IBookAndRating iBookAndRating = this.bookService.getBookAndRatingById(isbn);
        return iBookAndRating == null ?
                new ResponseEntity<>(new BookAndRatingAndDescription(), HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(new BookAndRatingAndDescription(iBookAndRating, this.bookService.getBookDescription(isbn)), HttpStatus.OK);
    }

    @GetMapping("/reviews/{isbn}")
    public List<BookReview> getBookReviews(@PathVariable("isbn") String isbn) {
        return this.bookService.getReviewsForBook(isbn);
    }

    @PostMapping("/rate")
    public StringWrapper rateBook(@RequestBody BookRatingDto bookRatingDto) {
        this.bookService.saveBookRating(bookRatingDto.userId, bookRatingDto.isbn, bookRatingDto.rating*2);
        return new StringWrapper("success");
    }

    @PostMapping("/un-rate")
    public StringWrapper unRateBook(@RequestBody BookRatingDto bookRatingDto) {
        this.bookService.deleteBookRating(bookRatingDto.userId, bookRatingDto.isbn);
        return new StringWrapper("success");
    }
    @PostMapping("/review")
    public StringWrapper reviewBook(@RequestBody BookReviewDto bookReviewDto) {
        this.bookService.saveReview(new Review(bookReviewDto.userId, bookReviewDto.isbn, bookReviewDto.review));
        return new StringWrapper("success");
    }


    @GetMapping("/{isbn}/rating/user/{userId}")
    public FloatWrapper getUserRating(@PathVariable("isbn") String isbn,
                                       @PathVariable("userId") Long userId) {
        BookRating bookRating = this.bookService.getBookRatingByUserAndIsbn(isbn, userId);
        return bookRating == null ?
                new FloatWrapper(0f)
                : new FloatWrapper(bookRating.getBookRating()/2f);
    }

}
