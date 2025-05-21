package com.example.chatservice.controller;

import com.example.chatservice.dto.request.ConversationRequestDTO;
import com.example.chatservice.dto.response.ConversationDetailDTO;
import com.example.chatservice.dto.response.ParticipantResponse;
import com.example.chatservice.dto.response.ResponseData;
import com.example.chatservice.entity.Conversation;
import com.example.chatservice.service.ConversationService;
import com.example.chatservice.service.ParticipantService;
import com.example.commonservice.model.OKMessage;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/chat-service/conversation")
public class ConversationController {

    @Autowired
    ConversationService conversationService;

    @Autowired
    ParticipantService participantService;

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

    @GetMapping("/{idConversation}")
    public ResponseData getConversationByid(@PathVariable String idConversation) {

        ConversationDetailDTO conversationDetailDTO = conversationService.getConversationWithParticipants(idConversation);

        return ResponseData.builder()
                .code(200)
                .message("Get cuoc tro chuyen voi ID thanh cong")
                .status(HttpStatus.OK)
                .data(conversationDetailDTO)
                .build();
    }


    @GetMapping("/idParticipant/{idAccount}")
    public ResponseData getConversationWithParticipant(@PathVariable String idAccount) {

        List<ParticipantResponse> responses = participantService.getAllParticipantByAccount(idAccount);

        List<Conversation> conversationList = responses.stream()
                .map(ParticipantResponse::getIdConversation) // Lấy ID của Conversation
                .map(id -> conversationService.getConversationById(id)) // Gọi service để lấy Conversation
                .filter(Objects::nonNull) // Loại bỏ kết quả null
                .toList(); // Chuyển thành danh sách


        return ResponseData.builder()
                .code(200)
                .message("Get cuoc tro chuyen thanh cong")
                .status(HttpStatus.OK)
                .data(conversationList)
                .build();
    }

}
