package com.example.chatservice.mapper;

import com.example.chatservice.dto.request.AddParticipantRequestDTO;
import com.example.chatservice.entity.ParticipantInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", imports = LocalDateTime.class)
public interface PaticipantInfoMapper {
    @Mapping(target = "dateJoin", expression = "java(LocalDateTime.now())")
    ParticipantInfo toParticipantInfo(AddParticipantRequestDTO dto);
}
