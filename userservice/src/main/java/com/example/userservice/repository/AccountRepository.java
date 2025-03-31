package com.example.userservice.repository;

import com.example.userservice.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    Optional<Account> findByEmail(String email);
    Optional<Account> findByPhoneNumber(String phoneNumber);
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);
    @Query("SELECT acc FROM Account acc WHERE LOWER(acc.email) LIKE LOWER(CONCAT('%', :email, '%'))")
    Page<Account> findAccountByEmail(@Param("email") String email, Pageable pageable);
}