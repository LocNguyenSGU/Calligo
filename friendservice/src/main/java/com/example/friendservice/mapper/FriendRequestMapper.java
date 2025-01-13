package com.example.friendservice.mapper;

import com.example.friendservice.dto.request.FriendRequestCreateRequest;
import com.example.friendservice.dto.request.FriendRequestUpdateStatusRequest;
import com.example.friendservice.entity.FriendRequest;
import org.mapstruct.Mapper;
import org.springframework.data.jpa.repository.JpaRepository;

@Mapper(componentModel = "spring")
public interface FriendRequestMapper {
    FriendRequest toFriendRequest(FriendRequestCreateRequest friendRequestCreateRequest);
    FriendRequest toFriendRequest(FriendRequest friendRequest);
}
