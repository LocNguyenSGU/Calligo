package com.example.userservice.service.Impl;

import com.example.userservice.dto.request.LoginRequest;
import com.example.userservice.entity.Account;
import com.example.userservice.repository.AccountRepository;
import com.example.userservice.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public int checkLogin(LoginRequest loginRequest) {
        // Tìm tài khoản dựa trên email
        Optional<Account> optionalAccount = accountRepository.findByEmail(loginRequest.getEmail());

        // Nếu không tìm thấy tài khoản, trả về 0 (không tồn tại)
        if(optionalAccount.isEmpty()) {
            return 0;
        }

        // Lấy tài khoản
        Account account = optionalAccount.get();

        // Kiểm tra mật khẩu nếu email đúng
        if (passwordEncoder.matches(loginRequest.getPassword(), account.getPassword())) {
            return 1;  // Đăng nhập thành công
        }

        // Nếu mật khẩu sai, trả về 2 (sai mật khẩu)
        return 2;
    }

    @Override
    public Optional<Account> getAccountByIdAccount(int idAccount) {
        return accountRepository.findById(idAccount);
    }
}
