package com.example.fileservice.dto;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileMessage {
    private String messageId;
    private String fileName;
    private String contentType;
    private byte[] fileData;
}
