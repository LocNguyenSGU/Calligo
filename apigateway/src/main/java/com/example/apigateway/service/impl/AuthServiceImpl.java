package com.example.apigateway.service.impl;

import com.example.apigateway.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private WebClient.Builder webClientBuilder;
    @Override
    public Mono<Void> validateToken(String token) {
        System.out.println("Starting token validation...");
        System.out.println("Token being sent: " + token);

        return webClientBuilder.build()
                .post()
                .uri("lb://userservice/api/v1/user-service/auth/validate-token")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .onStatus(
                        status -> status.isError(),
                        response -> {
                            System.out.println("Error response received from userservice: " + response.statusCode());
                            return Mono.error(new ResponseStatusException(UNAUTHORIZED, "Invalid access token"));
                        }
                )
                .bodyToMono(Void.class) // Chuyển đổi phản hồi thành Mono<Void>
                .doOnTerminate(() -> System.out.println("Token validation completed")) // Logging khi hoàn thành
                .doOnError(e -> System.out.println("Error occurred while validating token: " + e.getMessage())); // Logging khi có lỗi
    }
}
