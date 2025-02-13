package com.example.apigateway.service;

import com.example.apigateway.dto.response.AccountToFriend;
import reactor.core.publisher.Mono;

public interface AccountFriendService {
    Mono<AccountToFriend> fetchAndMergeData(String phone, String idAccountSource);
}
