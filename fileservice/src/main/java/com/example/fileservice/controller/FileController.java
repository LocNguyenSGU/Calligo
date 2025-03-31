package com.example.fileservice.controller;

import com.cloudinary.utils.ObjectUtils;
import com.example.fileservice.service.CloudinaryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/file-service/files")
@Slf4j
public class FileController {
    @Autowired
    private CloudinaryService cloudinaryService;
    @PostMapping("/upload")
    public ResponseEntity<List<Map<String, String>>> uploadFiles(@RequestParam("files") MultipartFile[] files) {
        try {
            List<Map<String, String>> uploadedFiles = Arrays.stream(files)
                    .parallel()
                    .map(file -> {
                        try {
                            log.info("Uploading file: {}", file.getOriginalFilename());
                            return cloudinaryService.uploadFile(file);
                        } catch (IOException e) {
                            log.error("Upload failed for file: {}", file.getOriginalFilename(), e);
                            throw new RuntimeException("Upload failed: " + file.getOriginalFilename(), e);
                        }
                    })
                    .collect(Collectors.toList());

            return ResponseEntity.ok(uploadedFiles);
        } catch (Exception e) {
            log.error("Internal Server Error", e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
