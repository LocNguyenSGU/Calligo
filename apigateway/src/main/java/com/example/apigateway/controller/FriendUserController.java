package com.example.apigateway.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/friend-user")
@Slf4j
public class FriendUserController {
    private final WebClient.Builder webClientBuilder;

    public FriendUserController(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @GetMapping("/phone/{phone}/idAccountSource/{idAccountSource}/idAccountTarget/{idAccountTarget}")
    public Mono<Map<String, Object>> getMergedData(
            @PathVariable String phone,
            @PathVariable String idAccountSource,
            @PathVariable String idAccountTarget) {

        String url1 = "lb://friendservice/api/v1/friend-service/friends/check-relationship/idAccountSource/"
                + idAccountSource + "/idAccountTarget/" + idAccountTarget;

        String url2 = "lb://userservice/api/v1/user-service/accounts/basic/phone/" + phone;

        log.info("🔍 Sending request to FriendService: {}", url1);
        Mono<Map> response1 = webClientBuilder.build()
                .get()
                .uri(url1)
                .retrieve()
                .bodyToMono(Map.class)
                .doOnSubscribe(sub -> log.info("📡 FriendService request started"))
                .doOnSuccess(response -> log.info("✅ FriendService response: {}", response))
                .doOnError(error -> log.error("❌ FriendService request failed: {}", error.getMessage()));

        log.info("🔍 Sending request to UserService: {}", url2);
        Mono<Map> response2 = webClientBuilder.build()
                .get()
                .uri(url2)
                .retrieve()
                .bodyToMono(Map.class)
                .doOnSubscribe(sub -> log.info("📡 UserService request started"))
                .doOnSuccess(response -> log.info("✅ UserService response: {}", response))
                .doOnError(error -> log.error("❌ UserService request failed: {}", error.getMessage()));

        return Mono.zip(response1, response2)
                .map(tuple -> {
                    Map<String, Object> mergedResponse = new HashMap<>();
                    mergedResponse.put("friendservice", tuple.getT1());
                    mergedResponse.put("userservice", tuple.getT2());

                    log.info("🔗 Merged Response: {}", mergedResponse);
                    return mergedResponse;
                })
                .doOnError(error -> log.error("❌ Error merging responses: {}", error.getMessage()));
    }

}
