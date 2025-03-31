package com.example.userservice.controller;

import com.example.commonservice.model.PageResponse;
import com.example.commonservice.model.ResponseDataMessage;
import com.example.userservice.dto.request.AccountUpdateRequest;
import com.example.userservice.dto.response.AccountBasicResponse;
import com.example.userservice.dto.response.AccountRelationResponse;
import com.example.userservice.dto.response.AccountResponse;
import com.example.userservice.repository.httpClient.FriendClient;
import com.example.userservice.security.JwtTokenProvider;
import com.example.userservice.service.AccountService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user-service/accounts")
@Slf4j
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
    @GetMapping("/phone/{phone}")
    public ResponseEntity<?> getInfoAccountByPhone(@PathVariable String phone) {
        AccountResponse accountResponse = accountService.getAccountResponseByPhone(phone);
        return new ResponseEntity<>(new ResponseDataMessage("Lay thong tin account", accountResponse), HttpStatus.OK);
    }
    @GetMapping("/basic/phone/{phone}")
    public ResponseEntity<?> getInfoAccountBasicByPhone(@PathVariable String phone) {
        AccountBasicResponse accountBasicResponse = accountService.getAccountBasicResponseByPhone(phone);
        return new ResponseEntity<>(new ResponseDataMessage("Lay thong tin account basic", accountBasicResponse), HttpStatus.OK);
    }
    @GetMapping("/basic/relation/phone/{phone}/idAccountSource/{idAccountSource}")
    public ResponseEntity<?> getInfoAccountBasicByPhoneWithRelation(@PathVariable String phone, @PathVariable int idAccountSource) {
        log.info("Received request: phone={}, idAccountSource={}", phone, idAccountSource);
        AccountRelationResponse accountRelationResponse = accountService.getInfoAccountBasicByPhoneWithRelation(phone, idAccountSource);
        return new ResponseEntity<>(new ResponseDataMessage("Lay thong tin account basic with relationship", accountRelationResponse), HttpStatus.OK);
    }

    @PutMapping("/{idAccount}")
    public ResponseEntity<?> updateUser(@PathVariable int idAccount, @Valid @RequestBody AccountUpdateRequest accountUpdateRequest) {
        AccountResponse accountResponse = accountService.updateAccount(accountUpdateRequest, idAccount);
        return new ResponseEntity<>(new ResponseDataMessage("Cap nhat thong tin account", accountResponse), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<?> getInfoAccountByEmail_Pagination(@RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "10") int size,
                                                   @RequestParam(defaultValue = "desc") String sortDirection,
                                                   @RequestParam(defaultValue = "") String email) {
        // Chỉ chấp nhận "asc" hoặc "desc" cho sortDirection
        if (!sortDirection.equalsIgnoreCase("asc") && !sortDirection.equalsIgnoreCase("desc")) {
            return ResponseEntity.badRequest().body(new ResponseDataMessage(400, "sortDirection phải là 'asc' hoặc 'desc'", null, HttpStatus.BAD_REQUEST));
        }

        PageResponse<AccountResponse> pageResponse = accountService.getAccounts(email, page, size, sortDirection);
        return new ResponseEntity<>(new ResponseDataMessage("Lay thong tin account co tim kiem email", pageResponse), HttpStatus.OK);
    }

}
