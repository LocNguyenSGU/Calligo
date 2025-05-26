package com.example.chatservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParticipantInfoResponse {
    private String idAccount;
    private String nickname;
    private String role;
    private LocalDateTime dateJoin;

    // Thông tin từ userservice
    private String firstName;
    private String lastName;
    private String imgAvatar;
}
