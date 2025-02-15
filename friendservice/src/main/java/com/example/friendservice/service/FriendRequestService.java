package com.example.friendservice.service;

import com.example.friendservice.dto.request.FriendRequestCreateRequest;
import com.example.friendservice.dto.request.FriendRequestUpdateStatusRequest;
import com.example.friendservice.dto.response.FriendRequestResponse;
import com.example.friendservice.dto.response.FriendRequestStatusResponse;
import com.example.friendservice.eenum.FriendRequestEnum;
import com.example.friendservice.entity.FriendRequest;
import org.springframework.data.domain.Page;

public interface FriendRequestService {
    int createFriendRequest(FriendRequestCreateRequest friendRequestCreateRequest);
    void updateFriendRequest(FriendRequestUpdateStatusRequest friendRequestUpdateStatusRequest, int idFriendRequest);

    Page<FriendRequestResponse> getFriendRequestsByIdAccountAndName(int idAccountReceive, String name, int page, int size, String sortDirection);

    FriendRequestStatusResponse getStatusFriendRequestBetweenTwoIdccount(int idAccountSource, int idAccountTarget);

    String getNote(int idAccountSent, int idAccountTarget);
}
