package com.app.readaholicv3.dataimport;

import com.app.readaholicv3.model.Book;
import com.app.readaholicv3.model.BookRating;
import com.app.readaholicv3.model.BookRatingId;
import com.app.readaholicv3.model.BxUser;
import com.app.readaholicv3.repository.BookRatingRepository;
import com.app.readaholicv3.repository.BookRepository;
import com.app.readaholicv3.repository.BxUserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

@RunWith(SpringRunner.class)
@SpringBootTest
@ComponentScan(value = "com.maximaLibri.maximaLibriV2")
@EnableTransactionManagement
public class ImportDatabase {
    private static final Logger LOGGER = Logger.getLogger(ImportDatabase.class.getName());

    @Autowired
    BookRepository bookRepository;

    @Autowired
    BxUserRepository bxUserRepository;

    @Autowired
    BookRatingRepository bookRatingRepository;

    @Autowired
    private EntityManager entityManager;

//    @Test
    public void createBooksFileInLocation() throws IOException{
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("src\\test\\java\\com\\app\\readaholicv3\\dataimport\\bx_books.cvs"));
        bufferedWriter.write("test string");
        bufferedWriter.close();
    }

//    @Test
//    @Transactional
//    @Rollback(false)
    public void importBooks() throws IOException {
        LOGGER.info("Books import started");
        BufferedReader bufferedReader = new BufferedReader(new FileReader("src\\test\\java\\com\\app\\readaholicv3\\dataimport\\bx_books.cvs"));
        LOGGER.info("BOOKS HEADER: " + bufferedReader.readLine());

        bookRepository.deleteAll();

        String delim = "|";
        String regex = Pattern.quote(delim);

        int successLines=0;
        int failedLines=0;
        String line="";
        while((line=bufferedReader.readLine())!=null) {
            String[] data = line.split(regex);
            try {
                Book book = new Book(
                        data[0],
                        data[1],
                        data[2],
                        Integer.parseInt(data[3]),
                        data[4],
                        data[5],
                        data[6],
                        data[7]
                );
                bookRepository.save(book);
                successLines++;
            }
            catch (Exception e) {
                LOGGER.info("FAILED : " + e.getMessage());
                LOGGER.info(line);
                failedLines++;
            }
        }

        LOGGER.info(successLines + " books imported : " + failedLines + " failed");
    }

//    @Test
    public void createBxUsersFileInLocation() throws IOException{
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("src\\test\\java\\com\\app\\readaholicv3\\dataimport\\bx_users.cvs"));
        bufferedWriter.write("test string");
        bufferedWriter.close();
    }

//    @Test
//    @Transactional
//    @Rollback(false)
    public void importBxUsers() throws IOException {
        LOGGER.info("BxUsers import started");
        BufferedReader bufferedReader = new BufferedReader(new FileReader("src\\test\\java\\com\\app\\readaholicv3\\dataimport\\bx_users.cvs"));
        LOGGER.info("BX_USERS_HEADER HEADER: " + bufferedReader.readLine());

        bxUserRepository.deleteAll();

        String delim = "|";
        String regex = Pattern.quote(delim);

        int successLines=0;
        int failedLines=0;
        String line="";
        while((line=bufferedReader.readLine())!=null) {
            String[] data = line.split(regex);
            try {
                BxUser bxUser;
                bxUser = data.length == 2 ?
                        new BxUser(Long.parseLong(data[0]), data[1], null) :
                        new BxUser(Long.parseLong(data[0]), data[1], Integer.parseInt(data[2]));
                bxUserRepository.save(bxUser);
                successLines++;
            }
            catch (Exception e) {
                LOGGER.info("FAILED : " + e.getMessage());
                LOGGER.info(line);
                failedLines++;
            }
        }

        LOGGER.info(successLines + " bx_users imported : " + failedLines + " failed");
    }

//    @Test
    public void createBookRatingsFileInLocation() throws IOException{
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("src\\test\\java\\com\\app\\readaholicv3\\dataimport\\bx_book_ratings.cvs"));
        bufferedWriter.write("test string");
        bufferedWriter.close();
    }

//    @Test
//    @Transactional
//    @Rollback(false)
    public void importBxBookRatings() throws IOException {
        LOGGER.info("BxBookRatings import started");
        BufferedReader bufferedReader = new BufferedReader(new FileReader("src\\test\\java\\com\\app\\readaholicv3\\dataimport\\bx_book_ratings.cvs"));
        LOGGER.info("BX_BOOK_RATINGS_HEADER HEADER: " + bufferedReader.readLine());

        bookRatingRepository.deleteAll();

        String delim = "|";
        String regex = Pattern.quote(delim);

        int successLines=0;
        int failedLines=0;
        String line="";
        while((line=bufferedReader.readLine())!=null) {
            String[] data = line.split(regex);
            try {
                BookRating bookRating = new BookRating(
                        new BookRatingId(Long.parseLong(data[0]), data[1]),
                        Integer.parseInt(data[2])
                );
                bookRatingRepository.save(bookRating);
                successLines++;
            }
            catch (Exception e) {
                LOGGER.info("FAILED : " + e.getMessage());
                LOGGER.info(line);
                failedLines++;
            }
        }

        LOGGER.info(successLines + " bx_book_ratings imported : " + failedLines + " failed");
    }
}
