package com.example.apigateway.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountToFriend {
    private int idAccount;
    private String firstName;
    private String lastName;
    private String imgAvatar;
    private boolean areFriends;
    private boolean isYourself;
}
