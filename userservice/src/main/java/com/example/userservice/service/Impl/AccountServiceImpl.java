package com.example.userservice.service.Impl;

import com.example.commonservice.exception.ResourceNotFoundException;
import com.example.commonservice.model.PageResponse;
import com.example.userservice.dto.request.AccountUpdateRequest;
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
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        return accountMapper.toAccountResponse(account);
    }

    @Override
    public AccountResponse getAccountResponseByPhone(String phone) {
        Account account = accountRepository.findByPhoneNumber(phone).orElseThrow(
                () -> new ResourceNotFoundException("Khong co account voi phone: " + phone));
        return accountMapper.toAccountResponse(account);
    }

    @Override
    public AccountBasicResponse getAccountBasicResponseByPhone(String phone) {
        Account account = accountRepository.findByPhoneNumber(phone).orElseThrow(
                () -> new ResourceNotFoundException("Khong co account voi phone: " + phone));
        return accountMapper.toAccountBasicResponse(account);
    }

    @Override
    public AccountBasicResponse getAccountBasicResponseByIdAccount(int idAccount) {
        Account account = accountRepository.findById(idAccount).orElseThrow(
                () -> new ResourceNotFoundException("Khong co account voi id: " + idAccount));
        return accountMapper.toAccountBasicResponse(account);
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

    @Override
    public AccountResponse updateAccount(AccountUpdateRequest accountUpdateRequest, int idAccount) {
        Account account = accountRepository.findById(idAccount).orElseThrow(() -> {
            log.error("Account not found with idAccount: {}", idAccount);
            return new ResourceNotFoundException("Khong co account voi idAccount: " + idAccount);
        });
        account.setFirstName(accountUpdateRequest.getFirstName());
        account.setLastName(accountUpdateRequest.getLastName());
        account.setAddress(accountUpdateRequest.getAddress());
        account.setDateOfBirth(accountUpdateRequest.getDateOfBirth());
        account.setImgAvatar(accountUpdateRequest.getImgAvatar());

        Account accountNew = accountRepository.save(account);
        return accountMapper.toAccountResponse(accountNew);
    }

    @Override
    public PageResponse<AccountResponse> getAccounts(String email, int page, int size, String sortDirection) {
        Sort sort = "asc".equalsIgnoreCase(sortDirection)
                ? Sort.by("createdAt").ascending()
                : Sort.by("createdAt").descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Account> accountPage = accountRepository.findAccountByEmail(email, pageable);
        Page<AccountResponse> accountResponsePage = accountPage.map(accountMapper::toAccountResponse);

        return new PageResponse<>(accountResponsePage);
    }

    @Override
    public Map<Integer, AccountBasicResponse> getAccountBasicByIds(List<Integer> ids) {
        Map<Integer, AccountBasicResponse> result = new HashMap<>();

        for (Integer id : ids) {
            Optional<Account> user = accountRepository.findById(id);
            user.ifPresent(u -> result.put(id, accountMapper.toAccountBasicResponse(u)));
        }
        return result;
    }
}
