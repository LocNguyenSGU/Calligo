package com.example.userservice.repository;

import com.example.userservice.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {
    boolean existsByTokenAndAccount_Email(String token, String email);

    void deleteByToken(String token);
}
