package com.example.friendservice.service.Impl;

import com.example.commonservice.exception.InvalidInputException;
import com.example.commonservice.exception.ResourceNotFoundException;
import com.example.commonservice.model.PageResponse;
import com.example.friendservice.dto.request.FriendRequestCreateRequest;
import com.example.friendservice.dto.request.FriendRequestUpdateStatusRequest;
import com.example.friendservice.dto.response.FriendRequestResponse;
import com.example.friendservice.dto.response.FriendRequestStatusResponse;
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
    public int createFriendRequest(FriendRequestCreateRequest friendRequestCreateRequest) {
        FriendRequest friendRequest = friendRequestMapper.toFriendRequest(friendRequestCreateRequest);
        friendRequest.setStatus(FriendRequestEnum.SENT);
        friendRequest.setTimeRequest(LocalDateTime.now());
        FriendRequest friendRequestCreated = friendRequestRepository.save(friendRequest);
        return friendRequestCreated.getIdFriendRequest();
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
    public PageResponse<FriendRequestResponse> getFriendRequestsByIdAccountAndName(int idAccountReceive, String name, int page, int size, String sortDirection) {
        Sort sort = "asc".equalsIgnoreCase(sortDirection)
                ? Sort.by("timeRequest").ascending()
                : Sort.by("timeRequest").descending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<FriendRequest> friendRequestPage = friendRequestRepository.
                findAllByIdAccountReceiveAndNotStatusAccepted(idAccountReceive, name, pageable);
        Page<FriendRequestResponse> pageResponse = friendRequestPage.map(friendRequestMapper::toFriendRequestResponse);

        return new PageResponse<>(pageResponse);
    }

    @Override
    public FriendRequestStatusResponse getStatusFriendRequestBetweenTwoIdccount(int idAccountSource, int idAccountTarget) {
        FriendRequest friendRequest = friendRequestRepository
                .findLatestFriendRequest(idAccountSource, idAccountTarget).orElseThrow(() ->
                        new ResourceNotFoundException("Khong co status hop le voi id account sent: " + idAccountSource + ", id account received: " + idAccountTarget));
        FriendRequestStatusResponse friendRequestStatusResponse = new FriendRequestStatusResponse();

        boolean areFriends = friendService.areFriends(idAccountSource, idAccountTarget);
        if(areFriends && friendRequest.getStatus().equals(FriendRequestEnum.ACCEPTED)) {
            friendRequestStatusResponse.setIdFriendRequest(friendRequest.getIdFriendRequest());
            friendRequestStatusResponse.setStatus(FriendRequestEnum.ACCEPTED);
        }else if(!areFriends){
            friendRequestStatusResponse.setIdFriendRequest(friendRequest.getIdFriendRequest());
            friendRequestStatusResponse.setStatus(friendRequest.getStatus());
        }
        return friendRequestStatusResponse;
    }

    @Override
    public String getNote(int idAccountSent, int idAccountTarget) {
        FriendRequest friendRequest = friendRequestRepository
                .findLatestFriendRequest(idAccountSent, idAccountTarget).orElseThrow(() ->
                        new ResourceNotFoundException("Khong co note hop le voi id account sent: " + idAccountSent + ", id account received: " + idAccountTarget));
        boolean areFriends = friendService.areFriends(idAccountSent, idAccountTarget);
        if(friendRequest.getStatus().equals(FriendRequestEnum.SENT)
                && !areFriends) {
            return friendRequest.getContent();
        }
        return "";
    }
}
