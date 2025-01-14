package com.example.friendservice.controller;

import com.example.commonservice.model.OKMessage;
import com.example.friendservice.dto.request.FriendRequestCreateRequest;
import com.example.friendservice.dto.request.FriendRequestUpdateStatusRequest;
import com.example.friendservice.service.FriendRequestService;
import com.example.friendservice.service.FriendService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/friend-requests")
public class FriendRequestController {
    @Autowired
    private FriendRequestService friendRequestService;
    @PostMapping
    public ResponseEntity<?> createFriendRequest(@Valid @RequestBody FriendRequestCreateRequest friendRequestCreateRequest) {
        friendRequestService.createFriendRequest(friendRequestCreateRequest);
        return new ResponseEntity<>(new OKMessage(200, "Tao friend request thanh cong", HttpStatus.OK), HttpStatus.OK);
    }
    @PutMapping("{idFriendRequest}/status")
    public ResponseEntity<?> createFriendRequest(@Valid @RequestBody FriendRequestUpdateStatusRequest friendRequestUpdateStatusRequest,@PathVariable int idFriendRequest) {
        friendRequestService.updateFriendRequest(friendRequestUpdateStatusRequest, idFriendRequest);
        return new ResponseEntity<>(new OKMessage(200, "Update status friend request thanh cong", HttpStatus.OK), HttpStatus.OK);
    }

}
