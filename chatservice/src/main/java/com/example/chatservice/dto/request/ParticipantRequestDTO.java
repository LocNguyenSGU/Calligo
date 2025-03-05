package com.example.chatservice.dto;

import com.example.chatservice.eenum.ParicipantRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParticipantRequestDTO {
    @NotBlank(message = "ID cuộc trò chuyện không được để trống")
    private String idConversation;

    @NotBlank(message = "ID tai khoan khong được để trống")
    private String idAccount;

    private ParicipantRole role;

    @NotNull(message = "Thoi gian tham gia khong duoc bo trong")
    private LocalDateTime dateJoin;

    private boolean isTyping;
}
