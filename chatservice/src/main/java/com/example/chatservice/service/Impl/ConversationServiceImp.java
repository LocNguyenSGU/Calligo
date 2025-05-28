package com.example.chatservice.service.Impl;

import com.example.chatservice.dto.request.AddParticipantRequestDTO;
import com.example.chatservice.dto.request.UpdateParticipantRequestDTO;
import com.example.chatservice.dto.response.ConversationResponse;
import com.example.chatservice.dto.response.ParticipantInfoResponse;
import com.example.chatservice.eenum.ConversationType;
import com.example.chatservice.eenum.ParicipantRole;
import com.example.chatservice.entity.Conversation;
import com.example.chatservice.entity.ParticipantInfo;
import com.example.chatservice.mapper.ConversationMapper;
import com.example.chatservice.mapper.PaticipantInfoMapper;
import com.example.chatservice.repository.ConversationRepository;
import com.example.chatservice.service.ConversationService;
import com.example.chatservice.service.MessageService;
import com.example.chatservice.service.UserClientService;
import com.example.commonservice.exception.AccessDeniedException;
import com.example.commonservice.exception.ResourceNotFoundException;
import com.example.commonservice.model.FriendshipCreatedEvent;
import com.example.commonservice.model.FriendshipDeleteEvent;
import com.example.commonservice.model.PageResponse;
import com.example.commonservice.model.ResponseDataMessage;
import com.example.commonservice.model.UserServiceModal.AccountBasicResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.*;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ConversationServiceImp implements ConversationService {
    @Autowired
    ConversationRepository conversationRepository;
    @Autowired
    PaticipantInfoMapper paticipantInfoMapper;
    @Autowired
    MessageService messageService;
    @Autowired
    UserClientService userClientService;
    @Autowired
    UserClientServiceImpl userClientServiceIml;
    @Override
    public List<Conversation> getAllConversation() {
        return conversationRepository.findAll();
    }

    @Override
    public Conversation getConversationById(String idConversation) {
        return conversationRepository.findByIdConversation(idConversation)
                .orElseThrow(() -> new ResourceNotFoundException("Không có conversation với id: " + idConversation));
    }
    @Override
    @Cacheable(value = "conversations", key = "#idAccount + '-' + #page + '-' + #size + '-' + #sortDirection")
    public PageResponse<ConversationResponse> getConversationsByAccountId(String idAccount, int page, int size, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase("asc") ?
                Sort.by("dateCreate").ascending() :
                Sort.by("dateCreate").descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Conversation> pageResult = conversationRepository.findByParticipantInfos_IdAccount(idAccount, pageable);

        // Lấy tất cả idAccount của participant trong toàn bộ trang
        Set<Integer> accountIds = pageResult.getContent().stream()
                .flatMap(convo -> convo.getParticipantInfos().stream())
                .map(ParticipantInfo::getIdAccount)
                .map(Integer::parseInt)
                .collect(Collectors.toSet());

        // Gọi API userservice để lấy thông tin user
        Map<Integer, AccountBasicResponse> userMap = userClientServiceIml
                .getUsersByIds(new ArrayList<>(accountIds));
//                .getBody();

        // Tạo danh sách ConversationResponse
        List<ConversationResponse> conversationResponses = pageResult.getContent().stream()
                .map(convo -> {
                    List<ParticipantInfoResponse> participantResponses = convo.getParticipantInfos().stream()
                            .map(participant -> {
                                AccountBasicResponse userInfo = userMap.get(Integer.parseInt(participant.getIdAccount()));
                                return new ParticipantInfoResponse(
                                        participant.getIdAccount(),
                                        participant.getNickname(),
                                        participant.getRole().name(),
                                        participant.getDateJoin(),
                                        userInfo != null ? userInfo.getFirstName() : null,
                                        userInfo != null ? userInfo.getLastName() : null,
                                        userInfo != null ? userInfo.getImgAvatar() : null
                                );
                            })
                            .collect(Collectors.toList());

                    return new ConversationResponse(
                            convo.getIdConversation(),
                            convo.getType(),
                            convo.getName(),
                            convo.getAvatar(),
                            convo.getDateCreate(),
                            convo.getDateUpdateMessage(),
                            convo.getLastMessageContent(),
                            convo.getNumberMember(),
                            participantResponses
                    );
                })
                .toList();

        return new PageResponse<>(
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalElements(),
                pageResult.getTotalPages(),
                conversationResponses
        );
    }

    @Override
    public void addParticipant(String conversationId, AddParticipantRequestDTO request) {
        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new ResourceNotFoundException("Conversation not found"));

        // Khởi tạo danh sách nếu null
        if (conversation.getParticipantInfos() == null) {
            conversation.setParticipantInfos(new ArrayList<>());
        }

        // Kiểm tra xem idAccount đã tồn tại trong cuộc hội thoại chưa
        boolean alreadyExists = conversation.getParticipantInfos().stream()
                .anyMatch(p -> p.getIdAccount().equals(request.getIdAccount()));

        if (alreadyExists) {
            throw new IllegalArgumentException("Participant already exists in this conversation");
        }

        // Ánh xạ và thêm mới
        ParticipantInfo newParticipant = paticipantInfoMapper.toParticipantInfo(request);

        conversation.getParticipantInfos().add(newParticipant);
        conversation.setNumberMember(conversation.getParticipantInfos().size());

        conversationRepository.save(conversation);
    }

    @Override
    public void removeParticipant(String conversationId, String idAccount) {
        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new ResourceNotFoundException("Conversation not found"));

        List<ParticipantInfo> participants = conversation.getParticipantInfos();
        if (participants == null || participants.isEmpty()) {
            throw new IllegalArgumentException("No participants in this conversation");
        }

        // Tìm và xóa người dùng khỏi danh sách
        boolean removed = participants.removeIf(p -> p.getIdAccount().equals(idAccount));

        if (!removed) {
            throw new ResourceNotFoundException("Participant not found in this conversation");
        }

        conversation.setNumberMember(participants.size());
        conversationRepository.save(conversation);
    }

    @Override
    public void updateParticipantInfo(String conversationId, String participantId, UpdateParticipantRequestDTO request) {
        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new ResourceNotFoundException("Conversation not found"));

        List<ParticipantInfo> participants = conversation.getParticipantInfos();
        if (participants == null || participants.isEmpty()) {
            throw new IllegalArgumentException("No participants in conversation");
        }

        // Kiểm tra người gửi yêu cầu có phải admin không
        boolean isRequesterAdmin = participants.stream()
                .anyMatch(p -> p.getIdAccount().equals(request.getRequesterId()) && p.getRole() == ParicipantRole.ADMIN);

        // Tìm participant cần sửa
        ParticipantInfo participant = participants.stream()
                .filter(p -> p.getIdAccount().equals(participantId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Participant not found"));

        // Sửa nickname nếu có
        if (request.getNickname() != null) {
            participant.setNickname(request.getNickname());
        }

        // Sửa role nếu có và requester là admin
        if (request.getRole() != null) {
            if (!isRequesterAdmin) {
                throw new AccessDeniedException("Only admin can update role");
            }
            participant.setRole(request.getRole());
        }

        conversationRepository.save(conversation);
    }

    @Override
    @KafkaListener(topics = "friendship-created", containerFactory = "kafkaListenerContainerFactory")
    public void createConversation(FriendshipCreatedEvent friendshipCreatedEvent) {

        // Tạo danh sách participant
        ParticipantInfo p1 = new ParticipantInfo(
                friendshipCreatedEvent.getSenderId(),
                ParicipantRole.USER,
                "",
                LocalDateTime.now()
        );
        ParticipantInfo p2 = new ParticipantInfo(
                friendshipCreatedEvent.getReceiverId(),
                ParicipantRole.USER,
                "",
                LocalDateTime.now()
        );

        List<ParticipantInfo> participants = List.of(p1, p2);
        System.out.println("Created participants: {}");

        // Tạo conversation
        Conversation conversation = new Conversation();
        conversation.setType(ConversationType.DOUBLE);
        conversation.setDateCreate(LocalDateTime.now());
        conversation.setNumberMember(2);
        conversation.setParticipantInfos(participants);

        // Optional: tạo id, avatar, name nếu cần
        conversation.setIdConversation(UUID.randomUUID().toString());
        conversation.setName(""); // sẽ hiển thị tên theo người kia
        conversation.setAvatar("");
        conversation.setLastMessageContent("");

        conversationRepository.save(conversation);
    }

    @Override
    @KafkaListener(topics = "friendship-delete", containerFactory = "kafkaListenerContainerFactory")
    public void deleteConversation(FriendshipDeleteEvent event) {
        // Tìm conversation DOUBLE giữa 2 người
        Conversation conversation = conversationRepository
                .findDoubleConversationByParticipants(event.getIdSender(), event.getIdReceiver())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không có cuộc trò chuyện hợp lệ giữa: " + event.getIdSender() + " và " + event.getIdReceiver()
                ));

        String conversationId = conversation.getIdConversation();

        // Xoá conversation + dữ liệu liên quan
        conversationRepository.deleteById(conversationId);
        messageService.deleteByIdConversation(conversationId);
    }

    @Override
    public void updateLastMessageInfo(String conversationId, String content, LocalDateTime lastTime) {
        conversationRepository.findByIdConversation(conversationId).ifPresent(conversation -> {
            conversation.setLastMessageContent(content);
            conversation.setDateUpdateMessage(lastTime);
            conversationRepository.save(conversation);
        });
    }
}
