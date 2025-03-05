package com.example.chatservice.dto.request;

import com.example.chatservice.dto.AttachmentDTO;
import com.example.chatservice.eenum.MessageEnum;
import com.example.chatservice.eenum.MessageType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageRequestDTO {
    @NotBlank(message = "ID cuộc trò chuyện không được để trống")
    private String idConversation;

    @NotBlank(message = "ID người gửi không được để trống")
    private String idAccountSent;

    @NotNull(message = "Trạng thái tin nhắn là bắt buộc")
    private MessageEnum status;

    @NotNull(message = "Loại tin nhắn là bắt buộc")
    private MessageType type;

    @Size(max = 3000, message = "Nội dung tin nhắn không được quá 3000 ký tự")
    private String content;

    private LocalDateTime timeSent = LocalDateTime.now(); // Mặc định là thời gian hiện tại

    private List<AttachmentDTO> attachments;

    private Map<String, String> reactions;

    private String replyToMessageId;

    private boolean isEdited = false;

    private List<String> editHistory;
}
