package com.example.userservice.controller;

import com.example.userservice.dto.request.LoginRequest;
import com.example.userservice.security.JwtTokenProvider;
import com.example.userservice.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private AccountService accountService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        // Kiểm tra đăng nhập
        int checkLogin = accountService.checkLogin(loginRequest);

        // Nếu email không tồn tại hoặc mật khẩu sai, trả về lỗi
        if (checkLogin == 0) {
            return new ResponseEntity<>("Email không tồn tại", HttpStatus.BAD_REQUEST);
        } else if (checkLogin == 2) {
            return new ResponseEntity<>("Mật khẩu sai", HttpStatus.UNAUTHORIZED);
        }

        // Nếu đăng nhập thành công, tạo accessToken và refreshToken
        String accessToken = jwtTokenProvider.generateToken(loginRequest.getEmail());
        String refreshToken = jwtTokenProvider.generateRefreshToken(loginRequest.getEmail());

        // Cấu hình cookie cho refreshToken
        ResponseCookie responseCookie = ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)  // Chỉ dùng Http, không thể truy cập từ JavaScript
                .secure(true)    // Chỉ gửi cookie qua HTTPS
                .path("/")       // Đường dẫn của API refresh token
                .maxAge(7 * 24 * 60 * 60) // Thời gian sống 7 ngày
                .build();

        // Trả về response với accessToken trong body và refreshToken trong cookie
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .body("Bearer " + accessToken);
    }
}
