package com.weiz.spb.repositories;

import com.weiz.spb.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByEmail(String email);
    Optional<User> findByEmailOrPhoneNumber(String email, String phoneNumber);
}
