package com.example.userservice.dto.response;

import com.example.userservice.dto.response.FriendService.FriendshipResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountRelationResponse {
    private AccountBasicResponse accountBasicResponse;
    private FriendshipResponse friendshipResponse;
}
