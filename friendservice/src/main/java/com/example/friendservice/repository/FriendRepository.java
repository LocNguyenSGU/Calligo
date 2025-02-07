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
    @Query("SELECT fr FROM Friend fr WHERE (fr.idAccountSent = :idAccount OR fr.idAccountReceive = :idAccount) " +
            "AND (fr.firstName LIKE %:name% OR fr.lastName LIKE %:name%)")
    Page<Friend> findFriendByIdAccountAndName(@Param("idAccount") int idAccount, @Param("name") String name, Pageable pageable);

    @Query("SELECT COUNT(f) FROM Friend f WHERE (f.idAccountSent = :userId AND f.idAccountReceive = :targetId) OR (f.idAccountSent = :targetId AND f.idAccountReceive = :userId)")
    int countFriendship(@Param("userId") int userId, @Param("targetId") int targetId);


}
