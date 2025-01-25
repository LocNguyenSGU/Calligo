package com.example.commonservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorObjectMessage {
    private int code = 400;
    private Object object;
    private String message;
    private HttpStatus status = HttpStatus.BAD_REQUEST;
}
