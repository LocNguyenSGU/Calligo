package com.example.userservice.service;

import com.example.userservice.dto.request.RefreshTokenCreateRequest;

public interface RefreshTokenService {
    void saveRefreshToken(RefreshTokenCreateRequest refreshTokenCreateRequest);

    boolean exitsRefreshTokenByEmail(String refreshToken, String email);

    void deleteByToken(String refreshToken);
}
