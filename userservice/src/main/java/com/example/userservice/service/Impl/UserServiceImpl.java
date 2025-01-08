package com.example.userservice.service.Impl;

import com.example.commonservice.exception.InvalidInputException;
import com.example.commonservice.exception.ResourceNotFoundException;
import com.example.userservice.dto.request.UserCreateRequest;
import com.example.userservice.dto.response.UserResponse;
import com.example.userservice.entity.Account;
import com.example.userservice.entity.User;
import com.example.userservice.mapper.UserMapper;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.service.AccountService;
import com.example.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AccountService accountService;

    @Override
    public UserResponse createUser(UserCreateRequest userCreateRequest) {
        User user = userMapper.toUser(userCreateRequest);
        int idAccount = userCreateRequest.getIdAccount();
        if(userRepository.existsByAccount_IdAccount(idAccount)) throw new InvalidInputException("Id account da ton tai");
        Account account = accountService.getAccountByIdAccount(idAccount).orElseThrow(
                () -> new ResourceNotFoundException("Khong tim thay account voi idAccount " + idAccount));
        user.setAccount(account);
        UserResponse userResponse = userMapper.toUserResponse(userRepository.save(user));
        return userResponse;
    }

    @Override
    public List<UserResponse> getAllUser() {
        return userRepository.findAll().stream()
                .map(userMapper::toUserResponse)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsByPhoneNumber(String phone) {
        return userRepository.existsByPhoneNumber(phone);
    }

    @Override
    public boolean existsByIdAccount(int idAccount) {
        return userRepository.existsByAccount_IdAccount(idAccount);
    }
}
