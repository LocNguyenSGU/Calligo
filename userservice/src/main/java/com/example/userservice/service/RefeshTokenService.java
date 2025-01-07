package com.example.userservice.service;

import com.example.userservice.dto.request.RefreshTokenCreateRequest;

public interface RefeshTokenService {
    void createRefreshToken(RefreshTokenCreateRequest refreshTokenCreateRequest);
}
