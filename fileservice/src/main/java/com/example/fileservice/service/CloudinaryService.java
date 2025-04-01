package com.example.fileservice.service;

import com.example.fileservice.dto.FileMessage;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface CloudinaryService {
    Map<String, String> uploadImage(MultipartFile file) throws IOException;
    Map<String, String> uploadVideo(MultipartFile file) throws IOException;
    Map<String, String> uploadOtherFile(MultipartFile file) throws IOException;
    Map<String, String> uploadFile(MultipartFile file) throws IOException;
    String deleteFile(String publicId) throws IOException;

    void consumeFileMessage(FileMessage fileMessage) throws IOException;
}
