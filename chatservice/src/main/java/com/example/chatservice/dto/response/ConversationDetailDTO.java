package com.example.chatservice.dto.response;

import com.example.chatservice.entity.Conversation;
import com.example.chatservice.entity.Participant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConversationDetailDTO {
    private Conversation conversation;
    private List<Participant> participants;
}
