package com.example.friendservice.service;

import com.example.friendservice.dto.request.FriendCreateRequest;
import com.example.friendservice.dto.request.FriendRequestCreateRequest;
import com.example.friendservice.dto.response.FriendResponse;
import com.example.friendservice.entity.Friend;
import com.example.friendservice.entity.FriendRequest;
import org.springframework.data.domain.Page;

public interface FriendService {
    void createFriendService(FriendRequest friendRequest);

    Page<FriendResponse> getAllFriendByIdAccount(int idAccount, String name, int page, int size, String sortDirection);
    public boolean areFriends(int idAccountSource, int idAccountTarget);
}
