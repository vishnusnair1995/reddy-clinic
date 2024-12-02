package com.reddyclinic.doctorportal.repository;
;

import com.reddyclinic.doctorportal.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Find a user by their email (you can add custom queries like this)
    Optional<User> findByEmail(String email);

    // You can define more custom query methods here
}

