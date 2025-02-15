package com.example.friendservice.repository;

import com.example.friendservice.entity.FriendRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest, Integer> {
    @Query("SELECT fr FROM FriendRequest fr WHERE fr.idAccountReceive = :idAccountReceive AND fr.status = 'SENT' AND (fr.firstName LIKE %:name% OR fr.lastName LIKE %:name%)")
    Page<FriendRequest> findAllByIdAccountReceiveAndNotStatusAccepted(
            @Param("idAccountReceive") int idAccountReceive,
            @Param("name") String name,
            Pageable pageable);
    @Query("SELECT fr FROM FriendRequest fr " +
            "WHERE fr.idAccountSent = :idAccountSent " +
            "AND fr.idAccountReceive = :idAccountReceive " +
            "ORDER BY fr.timeRequest DESC limit 1")
    Optional<FriendRequest> findLatestFriendRequest(@Param("idAccountSent") int idAccountSent,
                                                    @Param("idAccountReceive") int idAccountReceive);



}
