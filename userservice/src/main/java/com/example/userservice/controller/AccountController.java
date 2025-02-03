package com.example.userservice.controller;

import com.example.commonservice.model.ResponseDataMessage;
import com.example.userservice.dto.response.AccountResponse;
import com.example.userservice.security.JwtTokenProvider;
import com.example.userservice.service.AccountService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user-service/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @GetMapping("/email/{email}")
    public ResponseEntity<?> getInfoAccountByEmail(@PathVariable String email) {
        AccountResponse accountResponse = accountService.getAccountResponseByEmail(email);
        return new ResponseEntity<>(new ResponseDataMessage("Lay thong tin account", accountResponse), HttpStatus.OK);
    }
    @GetMapping("/info")
    public ResponseEntity<?> getInfoAccountByEmailHeader(HttpServletRequest request) {
        String accessToken = request.getHeader("AUTHORIZATION").substring(7);
        String email = jwtTokenProvider.getEmailFromJwtToken(accessToken);
        AccountResponse accountResponse = accountService.getAccountResponseByEmail(email);
        return new ResponseEntity<>(new ResponseDataMessage("Lay thong tin account", accountResponse), HttpStatus.OK);
    }
}
