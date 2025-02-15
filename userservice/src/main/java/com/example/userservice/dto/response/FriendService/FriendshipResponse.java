package com.example.userservice.dto.response.FriendService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FriendshipResponse {
    private boolean areFriends;
    private boolean isYourself;
    private String note;
}