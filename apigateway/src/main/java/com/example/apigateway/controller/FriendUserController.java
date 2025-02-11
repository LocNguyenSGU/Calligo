package com.example.apigateway.controller;

import com.example.apigateway.dto.response.AccountToFriend;
import com.example.apigateway.dto.response.ResponseDataMessage;
import com.example.apigateway.service.AccountFriendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/apigateway/friend-user")
@Slf4j
public class FriendUserController {
    private final WebClient.Builder webClientBuilder;
    @Autowired
    private AccountFriendService accountFriendService;

    public FriendUserController(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @GetMapping("/phone/{phone}/idAccountSource/{idAccountSource}/idAccountTarget/{idAccountTarget}")
    public Mono<ResponseDataMessage> getMergedData(
            @PathVariable String phone,
            @PathVariable String idAccountSource,
            @PathVariable String idAccountTarget) {
        return accountFriendService.fetchAndMergeData(phone, idAccountSource, idAccountTarget)
                .map(data -> new ResponseDataMessage("Info basic user to friendship", data));
    }

}
