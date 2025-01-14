package com.example.friendservice.repository;

import com.example.friendservice.entity.FriendRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest, Integer> {
    @Query("SELECT fr FROM FriendRequest fr WHERE fr.idAccountReceive = :idAccountReceive AND fr.status <> 'ACCEPTED'")
    Page<FriendRequest> findAllByIdAccountReceiveAndNotStatusAccepted(
            @Param("idAccountReceive") int idAccountReceive,
            Pageable pageable);
}
