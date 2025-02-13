package com.example.userservice.service.Impl;

import com.example.commonservice.exception.ResourceNotFoundException;
import com.example.userservice.dto.request.LoginRequest;
import com.example.userservice.dto.request.SignUpRequest;
import com.example.userservice.dto.response.AccountBasicResponse;
import com.example.userservice.dto.response.AccountRelationResponse;
import com.example.userservice.dto.response.AccountResponse;
import com.example.userservice.dto.response.FriendService.FriendshipResponse;
import com.example.userservice.entity.Account;
import com.example.userservice.mapper.AccountMapper;
import com.example.userservice.repository.AccountRepository;
import com.example.userservice.repository.httpClient.FriendClient;
import com.example.userservice.service.AccountService;
import com.example.userservice.service.RoleService;
import com.example.userservice.util.FriendshipResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private RoleService roleService;
    @Autowired
    private FriendClient friendClient;

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
    public AccountResponse getAccountResponseByEmail(String email) {
        Account account = accountRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Khong co account voi email: " + email));
        AccountResponse accountResponse = accountMapper.toAccountResponse(account);
        return accountResponse;
    }

    @Override
    public AccountResponse getAccountResponseByPhone(String phone) {
        Account account = accountRepository.findByPhoneNumber(phone).orElseThrow(
                () -> new ResourceNotFoundException("Khong co account voi phone: " + phone));
        AccountResponse accountResponse = accountMapper.toAccountResponse(account);
        return accountResponse;
    }

    @Override
    public AccountBasicResponse getAccountBasicResponseByPhone(String phone) {
        Account account = accountRepository.findByPhoneNumber(phone).orElseThrow(
                () -> new ResourceNotFoundException("Khong co account voi phone: " + phone));
        AccountBasicResponse accountBasicResponse = accountMapper.toAccountBasicResponse(account);
        return accountBasicResponse;
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

    @Override
    public AccountRelationResponse getInfoAccountBasicByPhoneWithRelation(String phone, int idAccountSource) {
        log.info("Start processing phone={} and idAccountSource={}", phone, idAccountSource);

        Account account = accountRepository.findByPhoneNumber(phone).orElseThrow(() -> {
            log.error("Account not found with phone: {}", phone);
            return new ResourceNotFoundException("Khong co account voi phone: " + phone);
        });

        int idAccountTarget = account.getIdAccount();
        log.info("Found account: idAccountTarget={}", idAccountTarget);

        AccountBasicResponse accountBasicResponse = accountMapper.toAccountBasicResponse(account);
        log.info("Mapped AccountBasicResponse: {}", accountBasicResponse);
        Object object = friendClient.checkFriendshipByIdAccount(idAccountSource, idAccountTarget);
        log.info("FriendshipResponse received object: {}", object);
        FriendshipResponse friendshipResponse = FriendshipResponseUtil.parseFriendshipResponse(object);
        log.info("FriendshipResponse received: {}", friendshipResponse);

        AccountRelationResponse accountRelationResponse = accountMapper.toAccountRelationResponse(accountBasicResponse, friendshipResponse);
        log.info("Final AccountRelationResponse: {}", accountRelationResponse);

        return accountRelationResponse;
    }
}
