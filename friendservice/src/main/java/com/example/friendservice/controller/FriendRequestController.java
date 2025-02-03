package com.example.friendservice.controller;

import com.example.commonservice.model.OKMessage;
import com.example.commonservice.model.ResponseDataMessage;
import com.example.commonservice.service.KafkaService;
import com.example.friendservice.dto.request.FriendRequestCreateRequest;
import com.example.friendservice.dto.request.FriendRequestUpdateStatusRequest;
import com.example.friendservice.dto.response.FriendRequestResponse;
import com.example.friendservice.service.FriendRequestService;
import com.example.friendservice.service.FriendService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/friend-service/friend-requestes")
public class FriendRequestController {
    @Autowired
    private FriendRequestService friendRequestService;
    @Autowired
    private KafkaService kafkaService;

    @PostMapping
    public ResponseEntity<?> createFriendRequest(@Valid @RequestBody FriendRequestCreateRequest friendRequestCreateRequest) {
        friendRequestService.createFriendRequest(friendRequestCreateRequest);
        // Giả sử người gửi lời mời là sender, người nhận là receiver
        String message = "Friend request sent from " + friendRequestCreateRequest.getIdAccountSent() + " to " + friendRequestCreateRequest.getIdAccountReceive();
        kafkaService.sendMessage("notifications", message);
        System.out.println("Sent friend request notification: " + message);

        return new ResponseEntity<>(new OKMessage(200, "Tao friend request thanh cong", HttpStatus.OK), HttpStatus.OK);
    }
    @PutMapping("{idFriendRequest}/status")
    public ResponseEntity<?> createFriendRequest(@Valid @RequestBody FriendRequestUpdateStatusRequest friendRequestUpdateStatusRequest,@PathVariable int idFriendRequest) {
        friendRequestService.updateFriendRequest(friendRequestUpdateStatusRequest, idFriendRequest);
        return new ResponseEntity<>(new OKMessage(200, "Update status friend request thanh cong", HttpStatus.OK), HttpStatus.OK);
    }

    @GetMapping("/account-receive/{idAccountReceive}")
    public ResponseEntity<?> getFriendRequestsByIdAccount(
            @PathVariable int idAccountReceive,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "desc") String sortDirection) {
        Page<FriendRequestResponse> responses = friendRequestService.getFriendRequestsByIdAccount(
                idAccountReceive, page, size, sortDirection);
        return new ResponseEntity<>(
                new ResponseDataMessage(200, "Lay danh sach loi moi ket ban thanh cong", responses, HttpStatus.OK)
                , HttpStatus.OK);
    }

}
