package com.example.friendservice.dto.response;

import com.example.friendservice.eenum.FriendRequestEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FriendRequestStatusResponse {
    private int idFriendRequest;
    private FriendRequestEnum status;
}
