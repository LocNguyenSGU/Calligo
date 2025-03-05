package com.example.chatservice.dto.request;

import com.example.chatservice.eenum.ParicipantRole;
import com.example.commonservice.customEnum.ValidEnum;
import jakarta.validation.constraints.NotBlank;
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

    @ValidEnum(enumClass = ParicipantRole.class)
    private ParicipantRole role;

    private LocalDateTime dateJoin = LocalDateTime.now();

    private boolean isTyping;
}
