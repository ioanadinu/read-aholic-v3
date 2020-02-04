package com.app.readaholicv3.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.app.readaholicv3.dto.*;
import com.app.readaholicv3.model.*;
import com.app.readaholicv3.repository.BookRatingRepository;
import com.app.readaholicv3.repository.ReviewRepository;
import com.app.readaholicv3.repository.RoleRepository;
import com.app.readaholicv3.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BookRatingRepository bookRatingRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    /** extrage si salveaza userul din formatul datelor din register */
    public User save(UserRegistrationDto registration){
        User user = new User();
        user.setUsername(registration.getUsername());
        user.setEmail(registration.getEmail());
        user.setPassword(BCrypt.withDefaults().hashToString(12, registration.getPassword().toCharArray()));
        user.setRoles(Collections.singletonList(roleRepository.findByName(RoleName.ROLE_USER)));
        user.setActive(true);
        return userRepository.save(user);
    }

    public String encodePassword(String password) {
        return BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }

    public UserIdAndRoles verifyLogin(UserLoginDto userLoginDto) {
        User user = findByEmail(userLoginDto.getEmail());
        if(user!=null && BCrypt.verifyer().verify(userLoginDto.getPassword().toCharArray(), user.getPassword()).verified)
            return new UserIdAndRoles(user.getId(), user.getRoles().stream().map(Role::getName).collect(Collectors.toSet()));
        else throw new RuntimeException("Bad Credentials");
    }

    /** intoarce istoricul voturilor unui user identificat prin id */
    public List<BookRating> getHistory(Long userId) {
        return bookRatingRepository.findRatingsByUserId(userId);
    }

    /** inregistreaza un vot in baza de date */
    public void rateBook(String isbn, Long userId, Integer rating) {
        BookRating bookRating = new BookRating(new BookRatingId(userId, isbn), rating);
        bookRatingRepository.save(bookRating);
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

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public CustomPageImpl<UserSummary> findUsersByIdOrUsername(String searchParameter, Integer page) {
        Pageable pageRequest = PageRequest.of(page, 10, Sort.by("id"));
        Page<User> users = searchParameter!= null ?
                userRepository.findAllByUsernameContainingIgnoreCaseOrEmailContainingIgnoreCase(searchParameter, searchParameter, pageRequest) :
                userRepository.findAll(pageRequest);
        return new CustomPageImpl<>(users.stream()
                .map(UserSummary::new)
                .collect(toList()),
                users.getTotalPages(), users.getTotalElements());
    }

    public void makeAdmin(Long id) {
        User user = this.userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN);
        user.getRoles().add(adminRole);
        this.userRepository.save(user);
    }
}

