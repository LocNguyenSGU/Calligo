package com.example.userservice.dto.request;

import com.example.userservice.entity.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

public class AccountCreateRequest {
    private String email;
    private String password;
    private LocalDateTime createdAt;
    private int idRole;
}
