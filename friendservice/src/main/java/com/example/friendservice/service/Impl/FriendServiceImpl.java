package com.example.friendservice.service.Impl;

import com.example.commonservice.exception.ResourceNotFoundException;
import com.example.commonservice.model.PageResponse;
import com.example.friendservice.dto.request.FriendCreateRequest;
import com.example.friendservice.dto.request.FriendRequestCreateRequest;
import com.example.friendservice.dto.response.FriendResponse;
import com.example.friendservice.eenum.FriendRequestEnum;
import com.example.friendservice.entity.Friend;
import com.example.friendservice.entity.FriendRequest;
import com.example.friendservice.mapper.FriendMapper;
import com.example.friendservice.repository.FriendRepository;
import com.example.friendservice.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
        friend.setFriendRequest(friendRequest);
        friendRepository.save(friend);
    }

    @Override
    public PageResponse<FriendResponse> getAllFriendByIdAccount(int idAccount, String name, int page, int size, String sortDirection) {
        Sort sort = "asc".equalsIgnoreCase(sortDirection)
                ? Sort.by("timeCreate").ascending()
                : Sort.by("timeCreate").descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Friend> friendPage = friendRepository.findFriendByIdAccountAndName(idAccount, name, pageable);
        Page<FriendResponse> friendResponsePage = friendPage.map(friendMapper::toFriendResponse);
        return new PageResponse<>(friendResponsePage);
    }

    @Override
    public boolean areFriends(int idAccountSource, int idAccountTarget) {
        return friendRepository.countFriendship(idAccountSource, idAccountTarget) > 0;
    }

    @Override
    public void deleteFriend(int idFriend) {
        if (!friendRepository.existsById(idFriend)) {
            throw new ResourceNotFoundException("Không có friend với id: " + idFriend);
        }
        try {
            friendRepository.deleteById(idFriend);
        } catch (Exception e) {
            throw new RuntimeException("Xóa bạn bè thất bại, vui lòng thử lại!");
        }
    }
}
