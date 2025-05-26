package com.example.chatservice.controller;

import com.example.chatservice.dto.request.AddParticipantRequestDTO;
import com.example.chatservice.dto.request.UpdateParticipantRequestDTO;
import com.example.chatservice.dto.response.ConversationResponse;
import com.example.chatservice.dto.response.ResponseData;
import com.example.chatservice.entity.Conversation;
import com.example.chatservice.service.ConversationService;
import com.example.commonservice.model.OKMessage;
import com.example.commonservice.model.PageResponse;
import com.example.commonservice.model.ResponseDataMessage;
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

    @GetMapping
    public ResponseData getConversations() {

        List<Conversation> responses = conversationService.getAllConversation();
        return ResponseData.builder()
                .code(200)
                .message("Get cuoc tro chuyen thanh cong")
                .status(HttpStatus.OK)
                .data(responses)
                .build();
    }

    @GetMapping("/idAccount/{idAccount}")
    public ResponseEntity<?> getConversationsByAccountId(
            @PathVariable String idAccount,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "desc") String sortDirection) {

        if (!sortDirection.equalsIgnoreCase("asc") && !sortDirection.equalsIgnoreCase("desc")) {
            return ResponseEntity.badRequest().body(new ResponseDataMessage(400, "sortDirection phải là 'asc' hoặc 'desc'", null, HttpStatus.BAD_REQUEST));
        }

        PageResponse<ConversationResponse> response = conversationService.getConversationsByAccountId(idAccount, page, size, sortDirection);

        if (response.getTotalElements() == 0) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ResponseDataMessage(204, "Không có cuộc trò chuyện nào", null, HttpStatus.NO_CONTENT));
        }

        return ResponseEntity.ok(new ResponseDataMessage(200, "Lấy danh sách cuộc trò chuyện thành công", HttpStatus.OK, response));
    }

    @PostMapping("/{conversationId}/participants")
    public ResponseEntity<?> addParticipant(@PathVariable String conversationId,
                                            @RequestBody AddParticipantRequestDTO request) {
        conversationService.addParticipant(conversationId, request);
        return ResponseEntity.ok(new OKMessage("Participant added successfully"));
    }

    @DeleteMapping("/{conversationId}/participants/{idAccount}")
    public ResponseEntity<?> removeParticipant(
            @PathVariable String conversationId,
            @PathVariable String idAccount
    ) {
        conversationService.removeParticipant(conversationId, idAccount);
        return ResponseEntity.ok(new OKMessage("Participant removed successfully"));
    }

    @PutMapping("/{conversationId}/participants/{participantId}")
    public ResponseEntity<?> updateParticipantInfo(
            @PathVariable String conversationId,
            @PathVariable String participantId,
            @RequestBody UpdateParticipantRequestDTO request
    ) {
        conversationService.updateParticipantInfo(conversationId, participantId, request);
        return ResponseEntity.ok(new OKMessage("Participant updated successfully"));
    }

}
