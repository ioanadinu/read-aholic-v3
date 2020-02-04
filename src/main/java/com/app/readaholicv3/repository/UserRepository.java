package com.app.readaholicv3.repository;

import com.app.readaholicv3.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findByUsername(String username);
    Page<User> findAllByUsernameContainingIgnoreCaseOrEmailContainingIgnoreCase(String searchParam1, String searchParam2, Pageable pageable);
}
