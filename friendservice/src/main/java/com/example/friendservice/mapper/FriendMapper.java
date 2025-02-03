package com.example.friendservice.mapper;

import com.example.friendservice.dto.request.FriendCreateRequest;
import com.example.friendservice.dto.request.FriendRequestCreateRequest;
import com.example.friendservice.dto.response.FriendResponse;
import com.example.friendservice.entity.Friend;
import com.example.friendservice.entity.FriendRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FriendMapper {
    Friend toFriend(FriendCreateRequest friendCreateRequest);
    Friend toFriend(FriendRequest friendRequest);

    FriendResponse toFriendResponse(Friend friend)
;}
