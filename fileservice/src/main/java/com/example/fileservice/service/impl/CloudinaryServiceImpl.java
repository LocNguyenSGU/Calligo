package com.example.fileservice.service.impl;

import com.example.fileservice.service.CloudinaryService;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class CloudinaryServiceImpl implements CloudinaryService {
    @Autowired
    private Cloudinary cloudinary;
    @Autowired
    @Value("${cloudinary.folder}")
    private String folder;


    @Override
    public Map<String, String> uploadImage(MultipartFile file) throws IOException {
        // Đọc ảnh vào BufferedImage
        BufferedImage originalImage = ImageIO.read(file.getInputStream());

        // Resize ảnh trước khi upload (giảm dung lượng)
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Thumbnails.of(originalImage)
                .size(800, 800) // Giảm kích thước xuống 800px
                .outputFormat("png") // Chuyển thành png
                .outputQuality(0.8) // Chất lượng 80%
                .toOutputStream(outputStream);

        Map uploadResult = cloudinary.uploader().upload(file.getBytes(),
                ObjectUtils.asMap(
                        "folder", folder + "/images",
                        "resource_type", "image"
                ));

        return Map.of(
                "url", (String) uploadResult.get("secure_url"),
                "public_id", (String) uploadResult.get("public_id"),
                "type", file.getContentType()
        );
    }
    @Override
    public Map<String, String> uploadVideo(MultipartFile file) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(),
                ObjectUtils.asMap(
                        "folder", folder + "/videos",
                        "resource_type", "video",
                        "bit_rate", "500k", // Giảm bitrate để tối ưu
                        "format", "mp4"
                ));

        return Map.of(
                "url", (String) uploadResult.get("secure_url"),
                "public_id", (String) uploadResult.get("public_id"),
                "type", file.getContentType()
        );
    }
    @Override
    public Map<String, String> uploadOtherFile(MultipartFile file) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(),
                ObjectUtils.asMap(
                        "folder", folder + "/files",
                        "resource_type", "auto"
                ));

        return Map.of(
                "url", (String) uploadResult.get("secure_url"),
                "public_id", (String) uploadResult.get("public_id"),
                "type", file.getContentType()
        );
    }

    @Override
    public Map<String, String> uploadFile(MultipartFile file) throws IOException {
        String contentType = file.getContentType();

        if (contentType.isEmpty() || contentType.isBlank()) {
            throw new IllegalArgumentException("Không thể xác định loại file!");
        }

        if (contentType.startsWith("image/")) {
            return uploadImage(file);
        } else if (contentType.startsWith("video/")) {
            return uploadVideo(file);
        } else {
            return uploadOtherFile(file);
        }
    }

    @Override
    public String deleteFile(String publicId) throws IOException {
        Map result = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        return (String) result.get("result"); // Trả về "ok" nếu xóa thành công
    }
}

