package com.example.chatservice.dto.request;

import com.example.chatservice.eenum.ParicipantRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateParticipantRequestDTO {
    private String nickname;
    private ParicipantRole role; // Có thể null nếu không muốn thay đổi
    private String requesterId;  // Người gửi yêu cầu (để check quyền admin)
}
