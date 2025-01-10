package com.example.apigateway.service;

import reactor.core.publisher.Mono;

public interface AuthService {
    Mono<Void> validateToken(String token);

}
