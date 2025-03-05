package com.example.chatservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseData {
    private int code;
    private String message;
    private Object data;
    private HttpStatus status;
}