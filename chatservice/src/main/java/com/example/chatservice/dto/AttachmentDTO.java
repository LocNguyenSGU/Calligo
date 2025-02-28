package com.example.chatservice.dto;

import com.example.chatservice.eenum.AttachmentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttachmentDTO {
    @NotBlank(message = "ID tin nhắn không được để trống")
    private String idMessage;

    @NotBlank(message = "ID cuộc trò chuyện không được để trống")
    private String idConversation;

    @NotNull(message = "Loại tệp đính kèm là bắt buộc")
    private AttachmentType type;

    @NotBlank(message = "URL không được để trống")
    @Pattern(regexp = "^(http|https)://.*$", message = "URL phải bắt đầu bằng http hoặc https")
    private String url;

    @NotBlank(message = "Kích thước file không được để trống")
    @Pattern(regexp = "^[0-9]+(MB|KB|GB)$", message = "Kích thước phải có đơn vị hợp lệ (KB, MB, GB)")
    private String size;

    private LocalDateTime timeUpload = LocalDateTime.now(); // Mặc định là thời gian hiện tại

    private int order;
}
