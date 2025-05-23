package com.example.friendservice.controller;

import com.example.commonservice.model.OKMessage;
import com.example.commonservice.model.PageResponse;
import com.example.commonservice.model.ResponseDataMessage;
import com.example.commonservice.service.KafkaService;
import com.example.friendservice.dto.request.FriendRequestCreateRequest;
import com.example.friendservice.dto.request.FriendRequestUpdateStatusRequest;
import com.example.friendservice.dto.response.FriendRequestResponse;
import com.example.friendservice.dto.response.FriendRequestStatusResponse;
import com.example.friendservice.service.FriendRequestService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        int idFriendRequested = friendRequestService.createFriendRequest(friendRequestCreateRequest);
        // Giả sử người gửi lời mời là sender, người nhận là receiver
        String message = "Friend request sent from " + friendRequestCreateRequest.getIdAccountSent() + " to " + friendRequestCreateRequest.getIdAccountReceive();
        kafkaService.sendMessage("notifications", message);
        System.out.println("Sent friend request notification: " + message);

        return new ResponseEntity<>(new ResponseDataMessage("Tao friend request thanh cong", idFriendRequested), HttpStatus.OK);
    }

    @PutMapping("{idFriendRequest}/status")
    public ResponseEntity<?> updateFriendRequest(@Valid @RequestBody FriendRequestUpdateStatusRequest friendRequestUpdateStatusRequest, @PathVariable int idFriendRequest) {
        friendRequestService.updateFriendRequest(friendRequestUpdateStatusRequest, idFriendRequest);

        return new ResponseEntity<>(new OKMessage(200, "Update status friend request thanh cong", HttpStatus.OK), HttpStatus.OK);
    }

    @GetMapping("/account-receive/{idAccountReceive}")
    public ResponseEntity<?> getFriendRequestsByIdAccountReceive(
            @PathVariable int idAccountReceive,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "desc") String sortDirection,
            @RequestParam(defaultValue = "") String name) {
        PageResponse<FriendRequestResponse> responses = friendRequestService.getFriendRequestsByIdAccountAndName(
                idAccountReceive, name, page, size, sortDirection);
        // Chỉ chấp nhận "asc" hoặc "desc" cho sortDirection
        if (!sortDirection.equalsIgnoreCase("asc") && !sortDirection.equalsIgnoreCase("desc")) {
            return ResponseEntity.badRequest().body(new ResponseDataMessage(400, "sortDirection phải là 'asc' hoặc 'desc'", null, HttpStatus.BAD_REQUEST));
        }

        if (responses.getTotalElements() == 0) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ResponseDataMessage(204, "Không có danh sách lời mời kết bạn nào phù hợp", null, HttpStatus.NO_CONTENT));
        }
        return new ResponseEntity<>(
                new ResponseDataMessage(200, "Lay danh sach loi moi ket ban thanh cong", HttpStatus.OK, responses)
                , HttpStatus.OK);
    }

    @GetMapping("/status/idAccountSent/{idAccountSent}/idAccountReceive/{idAccountReceive}")
    public ResponseEntity<?> getFriendRequestStatus(@PathVariable int idAccountSent,
                                                    @PathVariable int idAccountReceive) {
        FriendRequestStatusResponse friendRequestStatusResponse = friendRequestService.getStatusFriendRequestBetweenTwoIdccount(idAccountSent, idAccountReceive);
        if (friendRequestStatusResponse != null) {
            return ResponseEntity.ok(new ResponseDataMessage("Lay status giua 2 tai khoan", friendRequestStatusResponse));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No friend request found");
        }
    }


}
