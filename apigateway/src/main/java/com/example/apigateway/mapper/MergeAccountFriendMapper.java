package com.example.apigateway.mapper;

import com.example.apigateway.dto.response.AccountToFriend;
import com.example.apigateway.dto.responseFetch.AccountBasicResponse;
import com.example.apigateway.dto.responseFetch.FriendshipResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public class MergeAccountFriendMapper {
    public AccountToFriend toAccountToFriend(Map<String, Object> accountBasicResponse, Map<String, Object> friendshipResponse) {
        Map<String, Object> accountData = (Map<String, Object>) accountBasicResponse.get("data");
        Map<String, Object> friendData = (Map<String, Object>) friendshipResponse.get("data");

        AccountBasicResponse accountEntity = toAccountBasicResponse(accountData);
        FriendshipResponse friendEntity = toFriendshipResponse(friendData);

        return new AccountToFriend(accountEntity.getIdAccount(),
                accountEntity.getFirstName(),
                accountEntity.getLastName(),
                accountEntity.getImgAvatar(),
                friendEntity.isAreFriends(),
                friendEntity.isYourself());
    }

    private AccountBasicResponse toAccountBasicResponse(Map<String, Object> data) {
        AccountBasicResponse accountBasicResponse = new AccountBasicResponse();
        Object idAccount = data.get("idAccount");
        if (idAccount == null) {
            log.error("❌ idAccount is null in response data: {}", data);
        }
        accountBasicResponse.setIdAccount(idAccount != null ? Integer.valueOf(idAccount.toString()) : null);
//        accountBasicResponse.setIdAccount(Integer.valueOf(data.get("idAccount").toString()));
        accountBasicResponse.setFirstName((String) data.get("firstName"));
        accountBasicResponse.setLastName((String) data.get("lastName"));
        accountBasicResponse.setImgAvatar((String) data.get("imgAvatar"));
        return accountBasicResponse;
    }

    private FriendshipResponse toFriendshipResponse(Map<String, Object> data) {
        FriendshipResponse friendshipResponse = new FriendshipResponse();
        friendshipResponse.setAreFriends((boolean) data.get("areFriends"));
        friendshipResponse.setYourself((boolean) data.get("yourself"));
        return friendshipResponse;
    }
}
