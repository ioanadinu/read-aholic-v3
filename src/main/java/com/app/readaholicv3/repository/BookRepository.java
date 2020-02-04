package com.app.readaholicv3.repository;

import com.app.readaholicv3.dto.IBookAndRating;
import com.app.readaholicv3.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, String>, PagingAndSortingRepository<Book, String> {

    Page<Book> findAllByIsbnContainingIgnoreCaseOrTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(String searchParam1, String searchParam2, String searchParam3, Pageable pageable);

}
