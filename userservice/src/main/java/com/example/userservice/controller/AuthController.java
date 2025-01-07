package com.example.userservice.controller;

import com.example.commonservice.exception.ResourceNotFoundException;
import com.example.commonservice.exception.UnauthorizedException;
import com.example.userservice.dto.request.LoginRequest;
import com.example.userservice.dto.request.RefreshTokenCreateRequest;
import com.example.userservice.dto.response.ResponseData;
import com.example.userservice.security.JwtTokenProvider;
import com.example.userservice.service.AccountService;
import com.example.userservice.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private RefreshTokenService refreshTokenService;
    @Autowired
    private AccountService accountService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) throws Exception {
        // Kiểm tra đăng nhập
        int checkLogin = accountService.checkLogin(loginRequest);

        if (checkLogin == 0) throw  new UnauthorizedException("Sai thông tin đăng nhập " + loginRequest.getEmail());

        // Nếu đăng nhập thành công, tạo accessToken và refreshToken
        String accessToken = jwtTokenProvider.generateToken(loginRequest.getEmail());
        String refreshToken = jwtTokenProvider.generateRefreshToken(loginRequest.getEmail());

        // Tạo RefreshTokenCreateRequest với LocalDateTime
        RefreshTokenCreateRequest refreshTokenCreateRequest = new RefreshTokenCreateRequest(
                refreshToken, LocalDateTime.now(), jwtTokenProvider.getExpirationTimeTokenFromJwtToken(refreshToken) , loginRequest.getEmail()
        );
        refreshTokenService.saveRefreshToken(refreshTokenCreateRequest);

        // Cấu hình cookie cho refreshToken
        ResponseCookie responseCookie = ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)  // Chỉ dùng Http, không thể truy cập từ JavaScript
                .secure(true)    // Chỉ gửi cookie qua HTTPS
                .path("/")       // Đường dẫn của API refresh token
                .maxAge(7 * 24 * 60 * 60) // Thời gian sống 7 ngày
                .build();
        ResponseData responseData = new ResponseData(200, "Đang nhập thành công", accessToken, HttpStatus.OK);
        // Trả về response với accessToken trong body và refreshToken trong cookie
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .body(responseData);
    }
}
