package com.example.apigateway.service.impl;

import com.example.apigateway.dto.response.AccountToFriend;
import com.example.apigateway.mapper.MergeAccountFriendMapper;
import com.example.apigateway.service.AccountFriendService;
import jakarta.ws.rs.ServerErrorException;
import jakarta.ws.rs.core.Response;
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
    public Mono<AccountToFriend> fetchAndMergeData(String phone, String idAccountSource) {
        String userServiceUrl = "lb://userservice/api/v1/user-service/accounts/basic/phone/" + phone;

        log.info("🔍 Fetching from UserService: {}", userServiceUrl);
        Mono<Map> userResponse = fetchData(userServiceUrl, "UserService");

        // Trích xuất idAccountTarget từ userResponse với kiểm tra lỗi
        Mono<Integer> idAccountTargetMono = userResponse.flatMap(response -> {
            if (response == null || !response.containsKey("data")) {
                log.error("❌ UserService response missing 'data' field: {}", response);
                return Mono.error(new ServerErrorException(Response.Status.valueOf("Missing 'data' field in UserService response")));
            }

            Map<String, Object> data = (Map<String, Object>) response.get("data");
            if (!data.containsKey("idAccount")) {
                log.error("❌ UserService response missing 'idAccount': {}", data);
                return Mono.error(new ServerErrorException(Response.Status.valueOf("Missing 'idAccount' in UserService response")));
            }

            return Mono.just((Integer) data.get("idAccount"));
        });

        return Mono.zip(userResponse, idAccountTargetMono)
                .flatMap(tuple -> {
                    Map<String, Object> userMap = tuple.getT1();
                    int idAccountTarget = tuple.getT2();

                    String friendServiceUrl = "lb://friendservice/api/v1/friend-service/friends/check-relationship/idAccountSource/"
                            + idAccountSource + "/idAccountTarget/" + idAccountTarget;

                    log.info("🔍 Fetching from FriendService: {}", friendServiceUrl);
                    Mono<Map> friendResponse = fetchData(friendServiceUrl, "FriendService");

                    return Mono.zip(friendResponse, userResponse)
                            .map(innerTuple -> mergeAccountFriendMapper.toAccountToFriend(innerTuple.getT2(), innerTuple.getT1()));
                })
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
