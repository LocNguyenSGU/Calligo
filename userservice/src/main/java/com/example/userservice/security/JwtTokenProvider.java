package com.example.userservice.security;

import com.example.userservice.dto.response.HeaderPayload;
import com.example.userservice.service.BlacklistTokenService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expirationtime}")
    private long jwt_expiration;  // Thời gian sống của JWT (15 phút)

    @Value("${jwt.expirationrefreshtime}")
    private long jwt_refresh_expiration;  // Thời gian sống của refresh token (7 ngày)

    @Autowired
    private BlacklistTokenService blacklistTokenService;

    // Tạo JWT từ username
    public String generateToken(HeaderPayload headerPayload) { // access token
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
        // Tạo ID cho token
        String jws = Jwts.builder()
                .setSubject(headerPayload.getEmail())
                .claim("role", headerPayload.getRole())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwt_expiration))
                .signWith(key)
                .compact();
        return jws;
    }

    public String generateRefreshToken(String data) {
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
        // Tạo ID cho token
        String jws = Jwts.builder()
                .setSubject(data)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwt_refresh_expiration))
                .signWith(key)
                .compact();
        return jws;
    }

    // Lấy username từ JWT
    public String getEmailFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public LocalDateTime getExpirationTimeTokenFromJwtToken(String token) {
        Date expirationDate = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();

        // Chuyển từ Date sang LocalDateTime
        return expirationDate.toInstant()
                .atZone(ZoneId.systemDefault())  // Sử dụng múi giờ hệ thống
                .toLocalDateTime();
    }

    public boolean validateJwtToken(String token) {
        try {
            if (blacklistTokenService.existsByToken(token)) {
                return false; // Token đã bị thu hồi
            }
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            System.out.println("Invalid JWT signature: " + e.getMessage());
        } catch (MalformedJwtException e) {
            System.out.println("Invalid JWT token: " + e.getMessage());
        } catch (ExpiredJwtException e) {
            System.out.println("JWT token is expired: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.out.println("JWT token is unsupported: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("JWT claims string is empty: " + e.getMessage());
        }   catch (Exception e ){
            System.out.println("JWT exception " + e.getMessage());
        }
        return false;
    }
}
