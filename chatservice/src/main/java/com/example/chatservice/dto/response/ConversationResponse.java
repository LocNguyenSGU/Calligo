package com.example.chatservice.dto.response;

import com.example.chatservice.eenum.ConversationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConversationResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String idConversation;
    private ConversationType type;
    private String name;
    private String avatar;
    private LocalDateTime dateCreate;
    private LocalDateTime dateUpdateMessage;
    private String lastMessageContent;
    private int numberMember;
    private List<ParticipantInfoResponse> participantInfos;
}