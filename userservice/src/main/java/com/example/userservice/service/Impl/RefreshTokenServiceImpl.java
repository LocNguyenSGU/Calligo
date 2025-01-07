package com.example.userservice.service.Impl;

import com.example.commonservice.exception.ResourceNotFoundException;
import com.example.userservice.dto.request.RefreshTokenCreateRequest;
import com.example.userservice.entity.Account;
import com.example.userservice.entity.RefreshToken;
import com.example.userservice.mapper.RefreshTokenMapper;
import com.example.userservice.repository.RefreshTokenRepository;
import com.example.userservice.service.AccountService;
import com.example.userservice.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {
    @Autowired
    private RefreshTokenMapper refreshTokenMapper;
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;
    @Autowired
    private AccountService accountService;
    @Override
    public void saveRefreshToken(RefreshTokenCreateRequest refreshTokenCreateRequest) {
        RefreshToken refreshToken = refreshTokenMapper.toRefreshToken(refreshTokenCreateRequest);
        String email = refreshTokenCreateRequest.getEmail();
        Account account = accountService.getAccountByEmail(email)
                .orElseThrow(()
                        -> new ResourceNotFoundException("Khong tim thay tai khoan voi email " + email));
        refreshToken.setAccount(account);
        refreshToken.setActive(true);
        refreshTokenRepository.save(refreshToken);
    }
}
