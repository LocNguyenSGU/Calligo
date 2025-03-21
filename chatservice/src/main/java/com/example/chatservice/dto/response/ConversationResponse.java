package com.example.chatservice.dto.response;

import com.example.chatservice.eenum.ConversationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConversationResponse {
    private String idConversation;
    private ConversationType type;  // "double" hoặc "group"
    private String name;
    private String avatar;
    private LocalDateTime dateCreate;
    private String idLastMessage;
    private int numberMember;
}
