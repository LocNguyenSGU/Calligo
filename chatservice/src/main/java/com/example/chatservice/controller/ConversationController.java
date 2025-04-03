package com.example.chatservice.controller;

import com.example.chatservice.dto.request.ConversationRequestDTO;
import com.example.chatservice.dto.response.ConversationResponse;
import com.example.chatservice.dto.response.ParticipantResponse;
import com.example.chatservice.dto.response.ResponseData;
import com.example.chatservice.eenum.ConversationType;
import com.example.chatservice.entity.Conversation;
import com.example.chatservice.service.ConversationService;
import com.example.chatservice.service.ParticipantService;
import com.example.chatservice.service.RedisService;
import com.example.commonservice.model.OKMessage;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/chat-service/conversation")
public class ConversationController {

    @Autowired
    ConversationService conversationService;

    @Autowired
    ParticipantService participantService;

    @Autowired
    RedisService redisService;

    @PostMapping("/create")
    public ResponseEntity<?> createConversation(@Valid @RequestBody ConversationRequestDTO request) {

        conversationService.saveConversation(request);
        ResponseData responseData = new ResponseData();
        responseData.setCode(200);
        responseData.setMessage("Tạo cuoc tro chuyen thành công");
        responseData.setStatus(HttpStatus.OK);
        responseData.setData("");

        return new ResponseEntity<>(new OKMessage(200, "Tạo cuoc tro chuyen thành công", HttpStatus.OK), HttpStatus.OK);
    }


    @GetMapping
    public ResponseData getConversation() {

        List<Conversation> responses = conversationService.getAllConversation();
        return ResponseData.builder()
                .code(200)
                .message("Get cuoc tro chuyen thanh cong")
                .status(HttpStatus.OK)
                .data(responses)
                .build();
    }


    @GetMapping("/idParticipant/{idAccount}")
    public ResponseData getConversationWithParticipant(@PathVariable String idAccount) {

        List<ParticipantResponse> responses = participantService.getAllParticipantByAccount(idAccount);

        List<ConversationResponse> conversationList = responses.stream()
                .map(ParticipantResponse::getIdConversation) // Lấy ID của Conversation
                .map(id -> conversationService.getConversationById(id)) // Gọi service để lấy Conversation
                .filter(Objects::nonNull) // Loại bỏ kết quả null
                .map(conversation -> {
                    // Nếu là cuộc trò chuyện 2 người (double), cần tìm ID của đối phương
                    if (conversation.getType() == ConversationType.DOUBLE) {
                        List<ParticipantResponse> members = participantService.getParticipantbyIdConversation(conversation.getIdConversation());

                        // Tìm ID của đối phương
                        String opponentId = members.stream()
                                .map(ParticipantResponse::getIdAccount)
                                .filter(id -> !id.equals(idAccount)) // Loại bỏ ID của người đang đăng nhập
                                .findFirst()
                                .orElse(null); // Nếu không tìm thấy, trả về null

                        // Lấy trạng thái online từ Redis
                        if (opponentId != null) {
                            conversation.setStatus(redisService.isOnline(opponentId));
                            if (!conversation.isStatus()) {
                                Duration lastSeen = Duration.between(LocalDateTime.parse(redisService.getLastSeen(opponentId)), LocalDateTime.now());
                                if (lastSeen.toDays() > 0) {
                                    conversation.setLastSeen(lastSeen.toDays() + " ngày trước");
                                    return conversation;
                                } else if (lastSeen.toHours() > 0) {
                                    conversation.setLastSeen(lastSeen.toHours() + " giờ trước");
                                    return conversation;
                                } else if (lastSeen.toMinutes() > 0) {
                                    conversation.setLastSeen(lastSeen.toMinutes() + " phút trước");
                                    return conversation;
                                }

                            }
                        }
                    }
                    return conversation;
                })
                .toList(); // Chuyển thành danh sách


        return ResponseData.builder()
                .code(200)
                .message("Get cuoc tro chuyen thanh cong")
                .status(HttpStatus.OK)
                .data(conversationList)
                .build();
    }

}
