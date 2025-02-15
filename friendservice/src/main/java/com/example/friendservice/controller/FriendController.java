package com.example.friendservice.controller;

import com.example.commonservice.exception.ResourceNotFoundException;
import com.example.commonservice.model.OKMessage;
import com.example.commonservice.model.ResponseDataMessage;
import com.example.friendservice.dto.response.FriendRequestResponse;
import com.example.friendservice.dto.response.FriendResponse;
import com.example.friendservice.dto.response.FriendshipResponse;
import com.example.friendservice.eenum.FriendRequestEnum;
import com.example.friendservice.service.FriendRequestService;
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
    @Autowired
    private FriendRequestService friendRequestService;
    @GetMapping("/idAccount/{idAccount}")
    public ResponseEntity<?> getFriendByIdAccount(
            @PathVariable int idAccount,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "desc") String sortDirection,
            @RequestParam(defaultValue = "") String name) { // Đặt mặc định cho `name`

        // Chỉ chấp nhận "asc" hoặc "desc" cho sortDirection
        if (!sortDirection.equalsIgnoreCase("asc") && !sortDirection.equalsIgnoreCase("desc")) {
            return ResponseEntity.badRequest().body(new ResponseDataMessage(400, "sortDirection phải là 'asc' hoặc 'desc'", null, HttpStatus.BAD_REQUEST));
        }

        Page<FriendResponse> responses = friendService.getAllFriendByIdAccount(idAccount, name, page, size, sortDirection);

        if (responses.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ResponseDataMessage(204, "Không có bạn bè nào phù hợp", null, HttpStatus.NO_CONTENT));
        }

        return ResponseEntity.ok(new ResponseDataMessage(200, "Lấy danh sách bạn bè thành công", responses, HttpStatus.OK));
    }

    @GetMapping("/check-relationship/idAccountSource/{idAccountSource}/idAccountTarget/{idAccountTarget}")
    public ResponseEntity<?> checkFriendshipByIdAccount(
            @PathVariable int idAccountSource,
            @PathVariable int idAccountTarget) {
        FriendshipResponse friendshipResponse = new FriendshipResponse();
        friendshipResponse.setNote(friendRequestService.getNote(idAccountSource, idAccountTarget));
        friendshipResponse.setYourself(false);
        if(idAccountTarget == idAccountSource) {
            friendshipResponse.setYourself(true);
        }
        if(friendService.areFriends(idAccountSource, idAccountTarget)) {
            friendshipResponse.setAreFriends(true);
        } else {
            friendshipResponse.setAreFriends(false);
        }

        return ResponseEntity.ok(new ResponseDataMessage(200, "Kiem tra co phan ban be khong", friendshipResponse, HttpStatus.OK));
    }
    @DeleteMapping("/{idFriend}")
    public ResponseEntity<?> deleteFriend(@PathVariable int idFriend) {
        try {
            friendService.deleteFriend(idFriend);
            return ResponseEntity.ok(new OKMessage("Xóa bạn bè thành công"));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new OKMessage(404, e.getMessage(), HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new OKMessage(500, "Xóa bạn bè thất bại", HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }
}
