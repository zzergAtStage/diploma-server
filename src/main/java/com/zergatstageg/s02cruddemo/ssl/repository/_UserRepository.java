package com.zergatstageg.s02cruddemo.ssl.repository;

import com.zergatstageg.s02cruddemo.ssl.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface _UserRepository extends JpaRepository<User, Integer> {
    /**
     * Implements a method that returns user by its email
     * @param userEmail User email is used as nickname
     * @return Optional object container
     */
    Optional<User> findByUserEmail(String userEmail);
}
