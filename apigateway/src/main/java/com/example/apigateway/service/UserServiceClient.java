package com.example.apigateway.service;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "userservice")
public interface UserServiceClient {
}
