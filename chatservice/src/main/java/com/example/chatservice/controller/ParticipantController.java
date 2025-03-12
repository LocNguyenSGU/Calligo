package com.example.chatservice.controller;

import com.example.chatservice.dto.request.ParticipantRequestDTO;
import com.example.chatservice.dto.response.ParticipantResponse;
import com.example.chatservice.dto.response.ResponseData;
import com.example.chatservice.entity.Participant;
import com.example.chatservice.service.ConversationService;
import com.example.chatservice.service.ParticipantService;
import com.example.commonservice.model.OKMessage;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/chat-service/participant")
public class ParticipantController {
    @Autowired
    private ParticipantService participantService;

    @Autowired
    private ConversationService conversationService;

    @PostMapping("/create")
    public ResponseEntity<?> createParticipant(@Valid @RequestBody ParticipantRequestDTO request) {

        if (conversationService.getConversationById(request.getIdConversation()) == null) {
            return new ResponseEntity<>(new OKMessage(404, "Khong tim thay cuoc tro chuyen", HttpStatus.OK), HttpStatus.OK);
        }

        participantService.saveParticipant(request);


        return new ResponseEntity<>(new OKMessage(200, "Tạo participant thanh cong", HttpStatus.OK), HttpStatus.OK);
    }

    @GetMapping
    public ResponseData getParticipant() {

        List<Participant> response = participantService.getAllParticipant();
        return ResponseData.builder().code(200).message("Get Participant thanh cong").status(HttpStatus.OK).data(response).build();
    }

    @GetMapping("/idParticipant/{idParticipant}")
    public ResponseData getParticipantById(@PathVariable String idParticipant) {

        ParticipantResponse response = participantService.getParticipantbyId(idParticipant);
        if (response == null) {
            return ResponseData.builder().code(404).message("Khong tim thay participant").status(HttpStatus.OK).build();
        }
        return ResponseData.builder().code(200).message("Get Participant voi ID thanh cong").status(HttpStatus.OK).data(response).build();
    }

//    @GetMapping("/idConversation/{idConversation}")
//    public ResponseData getParticipantByIdConversation(@PathVariable String idConversation) {
//
//        ParticipantResponse response = participantService.getParticipantbyId(idConversation);
//        if (response == null) {
//            return ResponseData.builder().code(404).message("Khong tim thay participant").status(HttpStatus.OK).build();
//        }
//        return ResponseData.builder().code(200).message("Get Participant voi ID thanh cong").status(HttpStatus.OK).data(response).build();
//    }


}
