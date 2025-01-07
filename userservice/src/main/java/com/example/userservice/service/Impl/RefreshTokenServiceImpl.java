package com.example.userservice.service.Impl;

import com.example.userservice.dto.request.RefreshTokenCreateRequest;
import com.example.userservice.entity.Account;
import com.example.userservice.entity.RefreshToken;
import com.example.userservice.mapper.RefreshTokenMapper;
import com.example.userservice.repository.RefreshTokenRepository;
import com.example.userservice.service.AccountService;
import com.example.userservice.service.RefeshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RefreshTokenServiceImpl implements RefeshTokenService {
    @Autowired
    private RefreshTokenMapper refreshTokenMapper;
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;
    @Autowired
    private AccountService accountService;
    @Override
    public void createRefreshToken(RefreshTokenCreateRequest refreshTokenCreateRequest) {
        RefreshToken refreshToken = refreshTokenMapper.toRefreshToken(refreshTokenCreateRequest);
        Optional<Account> optionalAccount = accountService.getAccountByIdAccount(refreshTokenCreateRequest.getIdAccount());
        refreshToken.setAccount(optionalAccount.get());
        refreshTokenRepository.save(refreshToken);
    }
}
