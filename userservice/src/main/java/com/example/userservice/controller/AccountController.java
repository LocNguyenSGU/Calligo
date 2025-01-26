package com.example.userservice.controller;

import com.example.commonservice.model.ResponseDataMessage;
import com.example.userservice.dto.response.AccountResponse;
import com.example.userservice.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user-requests/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;
    @GetMapping("/email/{email}")
    public ResponseEntity<?> getInfoAccountByEmail(@PathVariable String email) {
        AccountResponse accountResponse = accountService.getAccountResponseByEmail(email);
        return new ResponseEntity<>(new ResponseDataMessage("Lay thong tin account", accountResponse), HttpStatus.OK);
    }
    @GetMapping("/info")
    public ResponseEntity<?> getInfoAccountByEmailHeader(@RequestHeader("email") String email) {
        AccountResponse accountResponse = accountService.getAccountResponseByEmail(email);
        return new ResponseEntity<>(new ResponseDataMessage("Lay thong tin account", accountResponse), HttpStatus.OK);
    }
}
