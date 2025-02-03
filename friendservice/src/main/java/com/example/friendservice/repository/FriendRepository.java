package com.example.friendservice.repository;

import com.example.friendservice.entity.Friend;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Integer> {
    @Query("SELECT fr FROM Friend fr WHERE fr.idAccountSent = :idAccount OR fr.idAccountReceive = :idAccount")
    Page<Friend> findAllByIdAccount(@Param("idAccount") int idAccount, Pageable pageable);
}
