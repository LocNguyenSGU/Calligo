package com.example.fileservice.service.impl;

import com.cloudinary.ProgressCallback;
import com.cloudinary.Uploader;
import com.example.commonservice.service.KafkaService;
import com.example.fileservice.dto.FileMessage;
import com.example.fileservice.dto.UploadCompleteMessage;
import com.example.fileservice.service.CloudinaryService;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.fileservice.service.WebSocketService;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Arrays;
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
    @Autowired
    private WebSocketService webSocketService;
    @Autowired
    private KafkaService kafkaService;

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

    private Map<String, String> upload_Kafka(byte[] bytes, String contentType, String messageId, String sessionId) throws IOException {

        if (contentType.isEmpty() || contentType.isBlank()) {
            throw new IllegalArgumentException("Không thể xác định loại file!");
        }

        if (contentType.startsWith("image/")) {
            return uploadImage_Kafka(bytes, messageId, sessionId);
        } else if (contentType.startsWith("video/")) {
            return uploadVideo_Kafka(bytes, messageId);
        } else {
            return uploadOtherFile_Kafka(bytes, messageId);
        }
    }

//    private Map<String, String> uploadImage_Kafka(byte[] bytes, String messageId, String sessionId) throws IOException {
//        // Convert byte array to BufferedImage
//        BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(bytes));
//
//        // Resize image before upload (reduce file size)
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        Thumbnails.of(originalImage)
//                .size(800, 800) // Resize to 800px
//                .outputFormat("png") // Convert to png
//                .outputQuality(0.8) // Set quality to 80%
//                .toOutputStream(outputStream);
//
//        byte[] resizedBytes = outputStream.toByteArray();
//
//        // Upload with progress callback using two long parameters
//        Map uploadResult = cloudinary.uploader().upload(resizedBytes,
//                ObjectUtils.asMap(
//                        "folder", folder + "/images",
//                        "resource_type", "image",
//                        "progress_callback", (ProgressCallback) (bytesUploaded, bytesTotal) -> {
//                            // Calculate the upload progress as a percentage
//                            double percentage = (double) bytesUploaded / bytesTotal * 100;
//                            log.info("Upload progress for messageId {}: {}%", messageId, percentage);
//
//                            // Send the upload progress to the WebSocket
//                            webSocketService.sendUploadProgress(sessionId, percentage);
//                        }
//                ));
//
//        // Get URL and public_id from the upload result
//        String url = (String) uploadResult.get("secure_url");
//        String publicId = (String) uploadResult.get("public_id");
//
//        // Send the result to the chat service
//        sendToChatService(messageId, url);
//
//        // Return URL, public_id, and the type of the uploaded file
//        return Map.of(
//                "url", url,
//                "public_id", publicId,
//                "type", "image"
//        );
//    }

    private Map<String, String> uploadImage_Kafka(byte[] bytes, String messageId, String sessionId) throws IOException {
        // Convert byte array to BufferedImage
        BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(bytes));

        // Resize image before upload (reduce file size)
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Thumbnails.of(originalImage)
                .size(800, 800) // Resize to 800px
                .outputFormat("png") // Convert to png
                .outputQuality(0.8) // Set quality to 80%
                .toOutputStream(outputStream);

        byte[] resizedBytes = outputStream.toByteArray();
        Map<String, Object> uploadResult;

        // Simulate progress (optional, if WebSocket updates are critical)
        int totalSize = resizedBytes.length;
        int chunkSize = 6 * 1024 * 1024; // 6MB
        int uploadedSize = 0;
        while (uploadedSize < totalSize) {
            uploadedSize += Math.min(chunkSize, totalSize - uploadedSize);
            double progress = ((double) uploadedSize / totalSize) * 100;
            log.info("Upload progress for messageId {}: {}%", messageId, progress);
            webSocketService.sendUploadProgress(sessionId, progress);
        }

        // Upload the full resized image to Cloudinary
        uploadResult = cloudinary.uploader().upload(resizedBytes,
                ObjectUtils.asMap(
                        "folder", folder + "/images",
                        "resource_type", "image"
                ));

        // Get URL and public_id from the upload result
        String url = (String) uploadResult.get("secure_url");
        String publicId = (String) uploadResult.get("public_id");

        log.info("Upload successful for messageId {}: URL={}", messageId, url);

        // Send the result to the chat service
        sendToChatService(messageId, url);

        // Return URL, public_id, and the type of the uploaded file
        return Map.of(
                "url", url,
                "public_id", publicId,
                "type", "image"
        );
    }

    private Map<String, String> uploadVideo_Kafka(byte[] bytes, String messageId) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(bytes,
                ObjectUtils.asMap(
                        "folder", folder + "/videos",
                        "resource_type", "video",
                        "bit_rate", "500k", // Giảm bitrate để tối ưu
                        "format", "mp4" // Đảm bảo video có định dạng mp4
                ));
        String url = (String) uploadResult.get("secure_url");
        sendToChatService(messageId, url);
        // Trả về URL và public_id của video sau khi upload thành công
        return Map.of(
                "url", (String) uploadResult.get("secure_url"),
                "public_id", (String) uploadResult.get("public_id"),
                "type", "video"
        );
    }
    private Map<String, String> uploadOtherFile_Kafka(byte[] bytes, String messageId) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(bytes,
                ObjectUtils.asMap(
                        "folder", folder + "/files", // Thư mục lưu trữ file
                        "resource_type", "auto" // Đặt resource_type thành "auto" để Cloudinary tự nhận dạng loại file
                ));

        String url = (String) uploadResult.get("secure_url");
        sendToChatService(messageId, url);

        // Trả về URL và public_id của file sau khi upload thành công
        return Map.of(
                "url", (String) uploadResult.get("secure_url"),
                "public_id", (String) uploadResult.get("public_id"),
                "type", "file" // Thêm thông tin kiểu file, có thể được thay đổi tùy vào file
        );
    }

    @Override
    public String deleteFile(String publicId) throws IOException {
        Map result = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        return (String) result.get("result"); // Trả về "ok" nếu xóa thành công
    }

    @Override
    @KafkaListener(topics = "upload-file", groupId = "file-group")
    public void consumeFileMessage(FileMessage fileMessage) throws IOException {
        String sessionId = "1234";
        Map<String, String> result = upload_Kafka(fileMessage.getFileData(), fileMessage.getContentType(),  fileMessage.getMessageId(), sessionId);
        System.out.println("Received file message: " + result);
        System.out.println("Id message: " + fileMessage.getMessageId());
    }
    public Map<String, String> uploadFile_Kafka(MultipartFile file, String messageId, String sessionId) throws IOException {
        log.info("Bắt đầu upload file: {}", file.getOriginalFilename());

        BufferedImage originalImage = ImageIO.read(file.getInputStream());

        // Resize ảnh trước khi upload
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Thumbnails.of(originalImage)
                .size(800, 800)
                .outputFormat("png")
                .outputQuality(0.8)
                .toOutputStream(outputStream);

        byte[] imageData = outputStream.toByteArray();
        long totalSize = imageData.length;

        log.info("File đã resize, kích thước sau resize: {} bytes", totalSize);

        // Tạo config cho upload
        Map<String, Object> uploadParams = new HashMap<>();
        uploadParams.put("folder", folder + "/images");
        uploadParams.put("resource_type", "image");
        uploadParams.put("chunk_size", 1024 * 1024); // 1MB cho mỗi chunk (tùy chỉnh)

        // Upload file lớn sử dụng phương thức upload
        Map<String, Object> uploadedFile = null;
        String uploadId = null;

        try {
            // Đảm bảo truyền đúng InputStream
            InputStream fileInputStream = file.getInputStream();

            // Upload file lên Cloudinary
            uploadedFile = cloudinary.uploader().upload(fileInputStream, uploadParams);
            // Upload file lớn với Cloudinary, Cloudinary sẽ tự động chia nhỏ file khi cần
            uploadId = String.valueOf(uploadedFile.get("public_id"));

            // Gửi thông tin về tiến trình qua WebSocket ngay khi bắt đầu upload
            log.info("Đang upload file lớn, public_id: {}", uploadId);

            // Polling tiến trình upload
            long uploadedSize = 0;
            boolean uploadInProgress = true;
            while (uploadInProgress) {
                try {
                    // Lấy thông tin tiến trình upload từ Cloudinary
                    Map<String, Object> statusResponse = cloudinary.api().resource(uploadId, ObjectUtils.asMap("resource_type", "image"));
                    Object progressObj = statusResponse.get("upload_progress");

                    if (progressObj != null) {
                        double progress = Double.parseDouble(progressObj.toString());
                        uploadedSize = (long) statusResponse.get("bytes_uploaded");

                        // Tính toán % tiến trình và gửi qua WebSocket
                        double progressPercentage = (uploadedSize / (double) totalSize) * 100;
                        log.info("Upload tiến trình: {}%", progressPercentage);

                        // Gửi tiến trình qua WebSocket cho người dùng
                        webSocketService.sendUploadProgress(sessionId, progressPercentage);

                        // Kiểm tra nếu upload đã hoàn tất
                        if (progressPercentage >= 100) {
                            uploadInProgress = false;
                            log.info("Upload hoàn tất, URL file: {}", uploadedFile.get("secure_url"));
                            sendToChatService(messageId, String.valueOf(uploadedFile.get("secure_url")));
                        }
                    }
                } catch (Exception e) {
                    log.error("Lỗi khi lấy thông tin tiến trình upload: {}", e.getMessage(), e);
                    break;
                }

                // Đợi một chút trước khi tiếp tục kiểm tra tiến trình
                try {
                    Thread.sleep(2000);  // Kiểm tra mỗi 2 giây
                } catch (InterruptedException e) {
                    log.error("Lỗi khi sleep: {}", e.getMessage(), e);
                }
            }

        } catch (Exception e) {
            log.error("Lỗi khi upload file lớn: {}", e.getMessage(), e);
            throw new IOException("Lỗi khi upload file lớn", e);
        }

        // Trả về thông tin về file đã upload
        if (uploadedFile != null) {
            return Map.of(
                    "url", String.valueOf(uploadedFile.get("secure_url")),
                    "public_id", String.valueOf(uploadedFile.get("public_id")),
                    "type", file.getContentType()
            );
        }

        log.error("Upload thất bại, không có dữ liệu phản hồi.");
        throw new IOException("Upload failed, no file returned");
    }
    private void sendToChatService(String messageId, String url) {
        UploadCompleteMessage uploadCompleteMessage = new UploadCompleteMessage(messageId, url);
        kafkaService.sendMessage("upload-done", uploadCompleteMessage);
    }

    public String uploadFile_Test(MultipartFile file) throws IOException {
        // Trả về URL của file đã được tải lên
        return null;
    }


}

