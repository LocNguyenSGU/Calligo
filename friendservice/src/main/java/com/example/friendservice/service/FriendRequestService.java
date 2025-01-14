package com.example.friendservice.service;

import com.example.friendservice.dto.request.FriendRequestCreateRequest;
import com.example.friendservice.dto.request.FriendRequestUpdateStatusRequest;
import com.example.friendservice.dto.response.FriendRequestResponse;
import com.example.friendservice.entity.FriendRequest;
import org.springframework.data.domain.Page;

public interface FriendRequestService {
    void createFriendRequest(FriendRequestCreateRequest friendRequestCreateRequest);
    void updateFriendRequest(FriendRequestUpdateStatusRequest friendRequestUpdateStatusRequest, int idFriendRequest);

    Page<FriendRequestResponse> getFriendRequestsByIdAccount(int idAccountReceive, int page, int size, String sortDirection);
}
