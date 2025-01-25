package com.example.userservice.service.Impl;

import com.example.commonservice.exception.ResourceNotFoundException;
import com.example.userservice.dto.request.LoginRequest;
import com.example.userservice.dto.request.SignUpRequest;
import com.example.userservice.entity.Account;
import com.example.userservice.mapper.AccountMapper;
import com.example.userservice.repository.AccountRepository;
import com.example.userservice.service.AccountService;
import com.example.userservice.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private RoleService roleService;

    @Override
    public int checkLogin(LoginRequest loginRequest) throws ResourceNotFoundException {
        // Tìm tài khoản dựa trên email
        Account account = accountRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Not account with " + loginRequest.getEmail()));

        // Kiểm tra mật khẩu nếu email đúng
        if (passwordEncoder.matches(loginRequest.getPassword(), account.getPassword())) {
            return 1;  // Đăng nhập thành công
        }
        return 0;
    }

    @Override
    public Optional<Account> getAccountByIdAccount(int idAccount) {
        return accountRepository.findById(idAccount);
    }

    @Override
    public Optional<Account> getAccountByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    @Override
    public void createAccount(SignUpRequest signUpRequest) {
        Account account = accountMapper.toAccount(signUpRequest);
        account.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        account.setCreatedAt(LocalDateTime.now());
        account.setActive(true);
        account.setRole(roleService.getRoleById(1));
        accountRepository.save(account);
    }

    @Override
    public boolean existsAccountByEmail(String email) {
        return accountRepository.existsByEmail(email);
    }
    public boolean existsAccountByPhoneNumber(String phoneNumber) {
        return accountRepository.existsByPhoneNumber(phoneNumber);
    }
}
