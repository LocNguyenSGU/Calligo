package com.example.friendservice.service;

import com.example.friendservice.dto.request.FriendCreateRequest;
import com.example.friendservice.dto.request.FriendRequestCreateRequest;
import com.example.friendservice.entity.FriendRequest;

public interface FriendService {
    void createFriendService(FriendRequest friendRequest);
}
