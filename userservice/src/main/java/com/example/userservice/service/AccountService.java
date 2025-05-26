package com.example.userservice.service;

import com.example.commonservice.model.PageResponse;
import com.example.userservice.dto.request.AccountUpdateRequest;
import com.example.userservice.dto.request.LoginRequest;
import com.example.userservice.dto.request.SignUpRequest;
import com.example.userservice.dto.response.AccountBasicResponse;
import com.example.userservice.dto.response.AccountRelationResponse;
import com.example.userservice.dto.response.AccountResponse;
import com.example.userservice.entity.Account;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface AccountService {
    int checkLogin(LoginRequest loginRequest) throws Exception;
    Optional<Account> getAccountByIdAccount(int idAccount);
    Optional<Account> getAccountByEmail(String email);
    AccountResponse getAccountResponseByEmail(String email);
    AccountResponse getAccountResponseByPhone(String phone);
    AccountBasicResponse getAccountBasicResponseByPhone(String phone);
    AccountBasicResponse getAccountBasicResponseByIdAccount(int idAccount);
    void createAccount(SignUpRequest signUpRequest);
    boolean existsAccountByEmail(String email);
    boolean existsAccountByPhoneNumber(String phoneNumber);
    AccountRelationResponse getInfoAccountBasicByPhoneWithRelation(String phone, int idAccountSource);
    AccountResponse updateAccount(AccountUpdateRequest userUpdateRequest, int idAccount);
    PageResponse<AccountResponse> getAccounts(String email, int page, int size, String sortDirection);
    Map<Integer, AccountBasicResponse> getAccountBasicByIds(List<Integer> ids);

}
