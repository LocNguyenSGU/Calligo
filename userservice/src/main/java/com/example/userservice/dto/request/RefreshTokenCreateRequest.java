package com.example.userservice.dto.request;

import com.example.userservice.entity.Account;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenCreateRequest {
    private String token;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private int idAccount;
    private boolean isActive;
}
