package com.example.chatservice.dto.request;

import com.example.chatservice.eenum.ConversationType;
import com.example.commonservice.customEnum.ValidEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConversationRequestDTO {

    @ValidEnum(enumClass = ConversationType.class)
    private ConversationType type;

    private String name;

    private String avatar;

    private LocalDateTime dateCreate = LocalDateTime.now(); // Mặc định là thời gian hiện tại

    private String idLastMessage;

    private int numberMember;
}
