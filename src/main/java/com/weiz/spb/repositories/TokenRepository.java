package com.weiz.spb.repositories;

import com.weiz.spb.entities.Token;
import com.weiz.spb.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, String> {
    Optional<Token> findByTokenValue(String tokenValue);
    List<Token> findByUser(User user);
}
