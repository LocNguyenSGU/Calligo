package com.example.userservice.service;

import com.example.userservice.dto.request.LoginRequest;
import com.example.userservice.entity.Account;

import java.util.Optional;

public interface AccountService {
    int checkLogin(LoginRequest loginRequest);
    Optional<Account> getAccountByIdAccount(int idAccount);
}
