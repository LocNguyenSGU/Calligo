package com.example.userservice.mapper;

import com.example.userservice.dto.request.RefreshTokenCreateRequest;
import com.example.userservice.entity.RefreshToken;
import org.mapstruct.Mapper;
@Mapper(componentModel = "spring")
public interface RefreshTokenMapper {
    RefreshToken toRefreshToken(RefreshTokenCreateRequest refreshTokenCreateRequest);

    RefreshTokenCreateRequest toRefreshTokenCreateRequest(RefreshToken refreshToken);
}
