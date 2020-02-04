package com.app.readaholicv3.recommender;

import com.app.readaholicv3.dto.IBookAndRating;
import com.app.readaholicv3.model.BookRating;
import com.app.readaholicv3.model.User;
import com.app.readaholicv3.repository.BookRatingRepository;
import com.app.readaholicv3.repository.BookRepository;
import com.app.readaholicv3.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecommenderService {

    @Autowired
    private UserService userService;

    @Autowired
    private BookRatingRepository bookRatingRepository;

    @Autowired
    private BookRepository bookRepository;

    /** gives 5 IBookAndRating items based on user cosine similarity */
    public List<IBookAndRating> recommendForUser(User user) {
        if(bookRatingRepository.findRatingsByUserId(user.getId()).size() < 3) return Collections.emptyList();

        SortedMap<Long, SortedMap<String, Integer> > utilityMatrix = makeUtilityMatrix();
        SortedMap<Long, Float> similarityList = new TreeMap<>();
        Set<Long> usersIdSet = utilityMatrix.keySet();
        SortedMap<String, Integer> utilityMatrixRowUser = utilityMatrix.get(user.getId());
        for(Long id : usersIdSet) {
            Set<String> totalIsbns = new TreeSet<>();
            totalIsbns.addAll(utilityMatrixRowUser.keySet());
            SortedMap<String, Integer> utilityMatrixRow = utilityMatrix.get(id);
            totalIsbns.addAll(utilityMatrixRow.keySet());
            int sumUser = 0;
            int sumOtherUser = 0;
            int sumProducts = 0;
            for(String isbn : totalIsbns) {
                Integer rs = utilityMatrixRowUser.get(isbn);
                if(rs==null) rs = 0;
                Integer r = utilityMatrixRow.get(isbn);
                if(r==null) r=0;
                sumUser+=rs*rs;
                sumOtherUser+=r*r;
                sumProducts+=rs*r;
            }
            similarityList.put(id,(float)(sumProducts/(Math.sqrt(sumUser*sumOtherUser))));
        }
        List<Long> userIdSorted= usersIdSet.stream().filter(x -> similarityList.get(x)!=1).sorted(Comparator.comparing(x -> similarityList.get(x)).reversed()).collect(Collectors.toList());
        int nrCarti = 5;
        int i=0;
        int is=0;
        Set<String> userIsbns = utilityMatrix.get(user.getId()).keySet();
        Set<String> recommendedIsbns = new TreeSet<>();
        while(i<10) {
            SortedMap<String, Integer> utilityMatrixRow = utilityMatrix.get(userIdSorted.get(is));
            Set<String> difference = utilityMatrixRow.keySet();
            difference.removeAll(userIsbns);
            List<String> differenceList = difference.stream().sorted(Comparator.comparing(x -> utilityMatrixRow.get(x)).reversed())
                    .filter(x -> utilityMatrixRow.get(x)>=7).collect(Collectors.toList());
            recommendedIsbns.addAll(differenceList);
            i=recommendedIsbns.size();
            is++;
        }
//        return recommendedIsbns.stream().sorted(Comparator.comparing(x -> bookRatingRepository.getBookRating(x)))
//                .limit(5).map(x -> bookRatingRepository.findBookAndRatingById(x)).collect(Collectors.toList());
        List<IBookAndRating> result = new ArrayList<>();
        int resultSize = 0;
        for(String isbn: recommendedIsbns) {
            IBookAndRating b = bookRatingRepository.findBookAndRatingById(isbn);
            if(b!=null && resultSize<5) {
                result.add(b);
                resultSize++;
            }
        }
        return result;
    }

    /** returns the utility maxtrix for the recommender; meant for internal use and testing */
    private SortedMap<Long, SortedMap<String, Integer> > makeUtilityMatrix() {
        List<BookRating> bookRatingList = bookRatingRepository.findAll();
        SortedMap<Long, SortedMap<String, Integer> > utilityMatrix = new TreeMap<>();
        for(BookRating bookRating : bookRatingList) {
            SortedMap<String, Integer> ratingsByUser = utilityMatrix.get(bookRating.getBookRatingId().getUserId());
            if(ratingsByUser==null) {
                ratingsByUser = new TreeMap<>();
                utilityMatrix.put(bookRating.getBookRatingId().getUserId(),ratingsByUser);
            }
            ratingsByUser.put(bookRating.getBookRatingId().getIsbn(),bookRating.getBookRating());
        }
        return utilityMatrix;
    }

}

