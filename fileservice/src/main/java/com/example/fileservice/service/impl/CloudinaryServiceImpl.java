package com.example.fileservice.service.impl;

import com.example.fileservice.service.CloudinaryService;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class CloudinaryServiceImpl implements CloudinaryService {

    private final Cloudinary cloudinary;
    private final String folder;

    // Inject Cloudinary qua constructor
    public CloudinaryServiceImpl(Cloudinary cloudinary, @Value("${cloudinary.folder}") String folder) {
        this.cloudinary = cloudinary;
        this.folder = folder;
    }

    @Override
    public Map<String, String> uploadFile(MultipartFile file) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(),
                ObjectUtils.asMap(
                        "folder", folder,
                        "resource_type", "auto"
                ));

        Map<String, String> result = new HashMap<>();
        result.put("url", (String) uploadResult.get("secure_url"));
        result.put("type", file.getContentType());
        result.put("public_id", (String) uploadResult.get("public_id"));

        return result;
    }

    @Override
    public String deleteFile(String publicId) throws IOException {
        Map result = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        return (String) result.get("result"); // Trả về "ok" nếu xóa thành công
    }
}

