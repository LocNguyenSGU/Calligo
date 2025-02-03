package com.example.userservice.controller;

import com.example.commonservice.exception.InvalidInputException;
import com.example.commonservice.exception.ResourceNotFoundException;
import com.example.commonservice.exception.UnauthorizedException;
import com.example.commonservice.model.ErrorObjectMessage;
import com.example.commonservice.model.OKMessage;
import com.example.userservice.dto.request.LoginRequest;
import com.example.userservice.dto.request.RefreshTokenCreateRequest;
import com.example.userservice.dto.request.SignUpRequest;
import com.example.userservice.dto.response.AccountResponse;
import com.example.userservice.dto.response.HeaderPayload;
import com.example.userservice.dto.response.ResponseData;
import com.example.userservice.security.JwtTokenProvider;
import com.example.userservice.service.AccountService;
import com.example.userservice.service.RefreshTokenService;
import io.netty.handler.codec.http.Cookie;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
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
import java.util.*;

@RestController
@RequestMapping("/api/v1/user-service/auth")
@Tag(name = "API auth", description = "Api for auth")
public class AuthController {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private RefreshTokenService refreshTokenService;
    @Autowired
    private AccountService accountService;

    @Operation(summary = "Login",
    description = "Api for login",
    responses = {
            @ApiResponse (responseCode = "401", description = "Sai thong tin dang nhap"),
            @ApiResponse (responseCode = "200", description = "Danh nhap than cong"),
            @ApiResponse (responseCode = "400", description = "Sai thong tin dau vao")
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) throws Exception {
        // Kiểm tra đăng nhập
        int checkLogin = accountService.checkLogin(loginRequest);

        if (checkLogin == 0) throw  new UnauthorizedException("Sai thông tin đăng nhập " + loginRequest.getEmail());

        // Nếu đăng nhập thành công, tạo accessToken và refreshToken
        AccountResponse accountResponse = accountService.getAccountResponseByEmail(loginRequest.getEmail());
        HeaderPayload headerPayload = new HeaderPayload(accountResponse.getEmail(), accountResponse.getRole());
        String accessToken = jwtTokenProvider.generateToken(headerPayload);
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
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignUpRequest signUpRequest) {
        Map<String, String> errorMessages = new HashMap<>();

        // Kiểm tra email
        if(accountService.existsAccountByEmail(signUpRequest.getEmail())) {
            errorMessages.put("email", "Email đã tồn tại");
        }

        // Kiểm tra số điện thoại
        if(accountService.existsAccountByPhoneNumber(signUpRequest.getPhoneNumber())) {
            errorMessages.put("phoneNumber", "Số điện thoại đã tồn tại");
        }

        // Kiểm tra mật khẩu và RePassword
        if(!signUpRequest.isPasswordEquals()) {
            errorMessages.put("password", "Mật khẩu nhập lại không khớp");
        }

        // Nếu có lỗi, trả về tất cả lỗi
        if(!errorMessages.isEmpty()) {
            return new ResponseEntity<>(new ErrorObjectMessage(400, errorMessages, "Đã xảy ra lỗi khi tạo tài khoản", HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }

        // Nếu không có lỗi, tiếp tục tạo tài khoản
        accountService.createAccount(signUpRequest);
        ResponseData responseData = new ResponseData();
        responseData.setCode(200);
        responseData.setMessage("Tạo tài khoản thành công");
        responseData.setStatus(HttpStatus.OK);
        responseData.setData("");

        return new ResponseEntity<>(new OKMessage(200, "Tạo tài khoản thành công", HttpStatus.OK), HttpStatus.OK);
    }

    @PostMapping("/validate-token")
    public ResponseEntity<ResponseData> validateToken(HttpServletRequest request) {
        String accessToken = request.getHeader("AUTHORIZATION").substring(7);
        System.out.println("accessToken from auth-controller: " + accessToken);
        boolean isValidated = jwtTokenProvider.validateJwtToken(accessToken);
        ResponseData responseData = new ResponseData();

        if (isValidated) {
            // Nếu token hợp lệ
            responseData.setCode(200);
            responseData.setStatus(HttpStatus.OK);
            responseData.setMessage("Token hợp lệ");
            responseData.setData(accessToken);
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        }

        // Nếu token không hợp lệ
        responseData.setCode(401);
        responseData.setStatus(HttpStatus.UNAUTHORIZED);
        responseData.setMessage("Token không hợp lệ");
        responseData.setData(accessToken);
        return new ResponseEntity<>(responseData, HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<ResponseData> refreshToken(HttpServletRequest request) {
        try {
            String refreshToken = getRefreshTokenFromRequest(request);

            if (refreshToken == null) {
                throw new UnauthorizedException("refreshToken không tồn tại");
            }
            // Kiểm tra tính hợp lệ của refreshToken
            if (!jwtTokenProvider.validateJwtToken(refreshToken)) {
                throw new UnauthorizedException("Refresh token không hợp lệ hoặc đã hết hạn");
            }

            // Lấy email từ refreshToken
            String email = jwtTokenProvider.getEmailFromJwtToken(refreshToken);

            // Kiểm tra refreshToken có tồn tại trong cơ sở dữ liệu
            boolean isRefreshTokenValid = refreshTokenService.exitsRefreshTokenByEmail(refreshToken, email);
            if (!isRefreshTokenValid) {
                throw new UnauthorizedException("Refresh token không tồn tại trong hệ thống");
            }
            // Nếu đăng nhập thành công, tạo accessToken và refreshToken
            AccountResponse accountResponse = accountService.getAccountResponseByEmail(email);
            HeaderPayload headerPayload = new HeaderPayload(accountResponse.getEmail(), accountResponse.getRole());
            String newAccessToken = jwtTokenProvider.generateToken(headerPayload);

            // Tạo ResponseData chứa accessToken mới
            ResponseData responseData = new ResponseData(200, "Cấp lại access token thành công", newAccessToken, HttpStatus.OK);

            return ResponseEntity.ok(responseData);

        } catch (UnauthorizedException e) {
            // Xử lý lỗi xác thực
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseData(401, e.getMessage(), null, HttpStatus.UNAUTHORIZED));
        } catch (Exception e) {
            // Xử lý các lỗi khác
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseData(500, "Đã xảy ra lỗi trong quá trình cấp lại access token", null, HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        // Lấy refreshToken từ Cookie
        // Lấy refresh token từ cookie
        jakarta.servlet.http.Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            throw new UnauthorizedException("Không tìm thấy cookie trong request");
        }

        String refreshToken = getRefreshTokenFromRequest(request);

        // Xóa refreshToken trong cơ sở dữ liệu
        refreshTokenService.deleteByToken(refreshToken);

        // Xóa cookie refreshToken trên client
        ResponseCookie responseCookie = ResponseCookie.from("refreshToken", null)
                .httpOnly(true) // Chỉ dùng Http
                .secure(true)   // Chỉ gửi qua HTTPS
                .path("/")      // Đường dẫn API
                .maxAge(0)      // Thời gian sống 0 -> Xóa cookie
                .build();

        // Trả về response
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .body(new ResponseData(200, "Đăng xuất thành công", null, HttpStatus.OK));
    }

    private String getRefreshTokenFromRequest(HttpServletRequest httpServletRequest) {
        String refreshToken = null;

        // Lấy refresh token từ cookie
        jakarta.servlet.http.Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies != null) {
            for (jakarta.servlet.http.Cookie cookie : cookies) {
                if (cookie.getName().equals("refreshToken")) {
                    refreshToken = cookie.getValue();
                    System.out.println("refreshToken: " + refreshToken);
                }
            }
        }
        return refreshToken;
    }
    private String getRefreshTokenFromResquest(HttpServletRequest httpServletRequest) {
        String refreshToken = null;
        OKMessage okMessage = new OKMessage("ok nef");

        // Lấy refresh token từ cookie
        jakarta.servlet.http.Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies != null) {
            for (jakarta.servlet.http.Cookie cookie : cookies) {
                if (cookie.getName().equals("refreshToken")) {
                    refreshToken = cookie.getValue();
                    System.out.println("refreshToken: " + refreshToken);
                }
            }
        }
        return refreshToken;
    }

}
