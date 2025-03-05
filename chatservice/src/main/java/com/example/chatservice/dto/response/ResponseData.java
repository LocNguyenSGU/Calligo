package com.example.chatservice.dto.response;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseData {
    private int code;
    private String message;
    private Object data;
    private HttpStatus status;
}