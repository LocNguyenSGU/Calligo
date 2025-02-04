package com.example.friendservice.service.Impl;

import com.example.commonservice.exception.InvalidInputException;
import com.example.commonservice.exception.ResourceNotFoundException;
import com.example.friendservice.dto.request.FriendRequestCreateRequest;
import com.example.friendservice.dto.request.FriendRequestUpdateStatusRequest;
import com.example.friendservice.dto.response.FriendRequestResponse;
import com.example.friendservice.eenum.FriendRequestEnum;
import com.example.friendservice.entity.FriendRequest;
import com.example.friendservice.mapper.FriendRequestMapper;
import com.example.friendservice.repository.FriendRequestRepository;
import com.example.friendservice.service.FriendRequestService;
import com.example.friendservice.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class FriendRequestServiceImpl implements FriendRequestService {
    @Autowired
    private FriendRequestRepository friendRequestRepository;
    @Autowired
    private FriendRequestMapper friendRequestMapper;
    @Autowired
    private FriendService friendService;

    @Override
    public void createFriendRequest(FriendRequestCreateRequest friendRequestCreateRequest) {
        FriendRequest friendRequest = friendRequestMapper.toFriendRequest(friendRequestCreateRequest);
        friendRequest.setStatus(FriendRequestEnum.SENT);
        friendRequest.setTimeRequest(LocalDateTime.now());
        friendRequestRepository.save(friendRequest);
    }

    @Override
    @Transactional
    public void updateFriendRequest(FriendRequestUpdateStatusRequest friendRequestUpdateStatusRequest, int idFriendRequest) {
        FriendRequest friendRequest = friendRequestRepository.findById(idFriendRequest).orElseThrow(
                ()-> new ResourceNotFoundException("Khong co friend request voi id: " + idFriendRequest));
        FriendRequestEnum status = FriendRequestEnum.valueOf(friendRequestUpdateStatusRequest.getStatus());
        if(friendRequest.getStatus().equals(FriendRequestEnum.ACCEPTED) && !status.equals(FriendRequestEnum.ACCEPTED)) {
            throw new InvalidInputException("Khong the cap nhat trang thai khi status ban dau cua friend request id: " + idFriendRequest + " da la ACCEPTED");
        }
        if(!friendRequest.getStatus().equals(FriendRequestEnum.ACCEPTED) && status.equals(FriendRequestEnum.ACCEPTED)) {
            System.out.println("Trang thai cu: " + friendRequest.getStatus());
            System.out.println("Trang thai moi: " + status);
            System.out.println("Cap nhat trang thai va luu");
            friendService.createFriendService(friendRequest);
        } else {
            System.out.println("Khong cap nhat trang tahi");
        }
        friendRequest.setStatus(status);
        friendRequestRepository.save(friendRequest);
    }

    @Override
    public Page<FriendRequestResponse> getFriendRequestsByIdAccountAndName(int idAccountReceive, String name, int page, int size, String sortDirection) {
        Sort sort = "asc".equalsIgnoreCase(sortDirection)
                ? Sort.by("timeRequest").ascending()
                : Sort.by("timeRequest").descending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<FriendRequest> friendRequestPage = friendRequestRepository.findAllByIdAccountReceiveAndNotStatusAccepted(idAccountReceive, name, pageable);

        return friendRequestPage.map(friendRequestMapper::toFriendRequestResponse);
    }
}
