package com.example.userservice.mapper;

import com.example.userservice.dto.request.SignUpRequest;
import com.example.userservice.entity.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    Account toAccount(SignUpRequest signUpRequest);

}
