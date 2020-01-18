package com.app.readaholicv3.repository;

import com.app.readaholicv3.model.BxUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BxUserRepository extends JpaRepository<BxUser, Long> {
}