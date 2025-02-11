package com.example.apigateway.service.impl;

import com.example.apigateway.dto.response.AccountToFriend;
import com.example.apigateway.mapper.MergeAccountFriendMapper;
import com.example.apigateway.service.AccountFriendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
@Slf4j
public class AccountFriendServiceImpl implements AccountFriendService {
    @Autowired
    private WebClient.Builder webClientBuilder;
    @Autowired
    private MergeAccountFriendMapper mergeAccountFriendMapper;
    @Override
    public Mono<AccountToFriend> fetchAndMergeData(String phone, String idAccountSource, String idAccountTarget) {
        String friendServiceUrl = "lb://friendservice/api/v1/friend-service/friends/check-relationship/idAccountSource/"
                + idAccountSource + "/idAccountTarget/" + idAccountTarget;
        String userServiceUrl = "lb://userservice/api/v1/user-service/accounts/basic/phone/" + phone;

        log.info("🔍 Fetching from FriendService: {}", friendServiceUrl);
        Mono<Map> friendResponse = fetchData(friendServiceUrl, "FriendService");

        log.info("🔍 Fetching from UserService: {}", userServiceUrl);
        Mono<Map> userResponse = fetchData(userServiceUrl, "UserService");

        return Mono.zip(friendResponse, userResponse)
                .map(tuple -> mergeAccountFriendMapper.toAccountToFriend(tuple.getT2(), tuple.getT1()))
                .doOnError(error -> log.error("❌ Error merging responses: {}", error.getMessage()));
    }
    private Mono<Map> fetchData(String url, String serviceName) {
        return webClientBuilder.build()
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(Map.class)
                .doOnSubscribe(sub -> log.info("📡 {} request started", serviceName))
                .doOnSuccess(response -> log.info("✅ {} response: {}", serviceName, response))
                .doOnError(error -> log.error("❌ {} request failed: {}", serviceName, error.getMessage()));
    }
}
