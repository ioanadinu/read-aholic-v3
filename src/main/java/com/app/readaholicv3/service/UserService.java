package com.app.readaholicv3.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.app.readaholicv3.dto.IBookReview;
import com.app.readaholicv3.dto.UserRegistrationDto;
import com.app.readaholicv3.model.BookRating;
import com.app.readaholicv3.model.BookRatingId;
import com.app.readaholicv3.model.RoleName;
import com.app.readaholicv3.model.User;
import com.app.readaholicv3.repository.BookRatingRepository;
import com.app.readaholicv3.repository.ReviewRepository;
import com.app.readaholicv3.repository.RoleRepository;
import com.app.readaholicv3.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BookRatingRepository bookRatingRepository;

//    @Autowired
//    private BCrypt passwordEncoder;

    @Autowired
    private ReviewRepository reviewRepository;

    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public User save(User user) {
        user.setRoles(Collections.singletonList(roleRepository.findByName(RoleName.ROLE_USER)));
        return userRepository.save(user);
    }

    /** extrage si salveaza userul din formatul datelor din register */
    public User save(UserRegistrationDto registration){
        User user = new User();
        user.setUsername(registration.getUsername());
        user.setEmail(registration.getEmail());
        user.setPassword(BCrypt.withDefaults().hashToString(12, registration.getPassword().toCharArray()));
        user.setRoles(Collections.singletonList(roleRepository.findByName(RoleName.ROLE_USER)));
        return userRepository.save(user);
    }

    public String encodePassword(String password) {
        return BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }

//    @Override
//    /** return user with given email; method necesary for spring security */
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        User user = userRepository.findByEmail(email);
//        if (user == null){
//            throw new UsernameNotFoundException("Invalid username or password.");
//        }
//        return new org.springframework.security.core.userdetails.User(user.getEmail(),
//                user.getPassword(),
//                mapRolesToAuthorities(user.getRoles()));
//    }
//
//    /** transforms the role entity into the collection of strings; necesary for spring security */
//    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
//        return roles.stream()
//                .map(role -> new SimpleGrantedAuthority(role.getName().toString()))
//                .collect(Collectors.toList());
//    }

    /** intoarce istoricul voturilor unui user identificat prin id */
    public List<BookRating> getHistory(Long userId) {
        return bookRatingRepository.findRatingsByUserId(userId);
    }

    /** inregistreaza un vot in baza de date */
    public void rateBook(String isbn, Long userId, Integer rating) {
        BookRating bookRating = new BookRating(new BookRatingId(userId, isbn), rating);
        bookRatingRepository.save(bookRating);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Transactional
    public void disableUserById(Long id) {
        User user = findById(id);
        user.setActive(false);
        userRepository.save(user);
    }

    @Transactional
    public void enableUserById(Long id) {
        User user = findById(id);
        user.setActive(true);
        userRepository.save(user);
    }

    /** intoarce istoricul review-urilor unui user identificat prin id */
    public List<IBookReview> getReviewsByUser(Long id) {
        return reviewRepository.findReviewsByUser(id);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}

