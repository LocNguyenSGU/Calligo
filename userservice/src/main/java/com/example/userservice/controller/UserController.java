package com.example.userservice.controller;

import com.example.commonservice.exception.InvalidInputException;
import com.example.userservice.dto.request.UserCreateRequest;
import com.example.userservice.dto.response.ResponseData;
import com.example.userservice.dto.response.UserResponse;
import com.example.userservice.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<ResponseData> createUser(@Valid @RequestBody UserCreateRequest userCreateRequest) {
        int idAccount = userCreateRequest.getIdAccount();
        String phoneNumber = userCreateRequest.getPhoneNumber();
        if(!phoneNumber.isEmpty()) {
            if(userService.existsByPhoneNumber(phoneNumber)) throw new InvalidInputException("Phone number da ton tai");
        }
        if(userService.existsByIdAccount(idAccount)) throw new InvalidInputException("Id account da ton tai");
        UserResponse resp = userService.createUser(userCreateRequest);
        ResponseData responseData = new ResponseData();
        responseData.setData(resp);
        responseData.setMessage("Tao user thanh cong");
        responseData.setStatus(HttpStatus.OK);
        responseData.setCode(200);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllUser() {
        System.out.println("Goi get all usser");
        List<UserResponse> resp = userService.getAllUser();
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

}
