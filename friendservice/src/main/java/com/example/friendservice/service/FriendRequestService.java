package com.example.friendservice.service;

import com.example.friendservice.dto.request.FriendRequestCreateRequest;
import com.example.friendservice.dto.request.FriendRequestUpdateStatusRequest;

public interface FriendRequestService {
    void createFriendRequest(FriendRequestCreateRequest friendRequestCreateRequest);
    void updateFriendRequest(FriendRequestUpdateStatusRequest friendRequestUpdateStatusRequest, int idFriendRequest);
}
