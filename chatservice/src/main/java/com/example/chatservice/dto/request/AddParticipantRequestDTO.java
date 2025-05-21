package com.example.chatservice.dto.request;

import com.example.chatservice.eenum.ParicipantRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddParticipantRequestDTO {
    private String idAccount;
    private String nickname;
    private ParicipantRole role; // ADMIN hoặc MEMBER
}
