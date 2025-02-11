package com.example.apigateway.dto.responseFetch;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FriendshipResponse {
    private boolean areFriends;
    private boolean isYourself;
}
