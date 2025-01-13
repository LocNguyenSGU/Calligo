package com.example.friendservice.service.Impl;

import com.example.friendservice.dto.request.FriendCreateRequest;
import com.example.friendservice.dto.request.FriendRequestCreateRequest;
import com.example.friendservice.entity.Friend;
import com.example.friendservice.entity.FriendRequest;
import com.example.friendservice.mapper.FriendMapper;
import com.example.friendservice.repository.FriendRepository;
import com.example.friendservice.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class FriendServiceImpl implements FriendService {
    @Autowired
    private FriendRepository friendRepository;

    @Autowired
    private FriendMapper friendMapper;


    @Override
    public void createFriendService(FriendRequest friendRequest) {
        Friend friend = friendMapper.toFriend(friendRequest);
        friend.setTimeCreate(LocalDateTime.now());
        friendRepository.save(friend);
    }
}
