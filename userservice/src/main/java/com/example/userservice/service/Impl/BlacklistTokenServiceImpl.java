package com.example.userservice.service.Impl;

import com.example.userservice.repository.BlacklistTokenRepository;
import com.example.userservice.service.BlacklistTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlacklistTokenServiceImpl implements BlacklistTokenService {
    @Autowired
    private BlacklistTokenRepository blacklistTokenRepository;
    @Override
    public boolean existsByToken(String token) {
        return blacklistTokenRepository.existsByToken(token);
    }
}
