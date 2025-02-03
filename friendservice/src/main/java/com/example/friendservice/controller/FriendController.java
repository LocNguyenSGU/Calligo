package com.example.friendservice.controller;

import com.example.commonservice.model.ResponseDataMessage;
import com.example.friendservice.dto.response.FriendRequestResponse;
import com.example.friendservice.dto.response.FriendResponse;
import com.example.friendservice.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/friend-service/friends")
public class FriendController {
    @Autowired
    private FriendService friendService;
    @GetMapping("/idAccount/{idAccount}")
    public ResponseEntity<?> getFriendByIdAccount(
            @PathVariable int idAccount,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "desc") String sortDirection) {
        Page<FriendResponse> responses = friendService.getAllFriendByIdAccount(
                idAccount, page, size, sortDirection);
        return new ResponseEntity<>(
                new ResponseDataMessage(200, "Lay danh sach ban be thanh cong", responses, HttpStatus.OK)
                , HttpStatus.OK);
    }
}
