package com.example.userservice.repository.httpClient;

import com.example.userservice.dto.response.FriendService.FriendshipResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "friendservice", url = "http://localhost:8083/api/v1/friend-service")
public interface FriendClient {
    @GetMapping(value = "friends/check-relationship/idAccountSource/{idAccountSource}/idAccountTarget/{idAccountTarget}")
    Object checkFriendshipByIdAccount(@PathVariable int idAccountSource, @PathVariable int idAccountTarget);

}
