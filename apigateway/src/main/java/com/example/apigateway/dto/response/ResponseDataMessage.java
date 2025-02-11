package com.example.apigateway.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
@Data
@AllArgsConstructor
public class ResponseDataMessage {
    private int code = 200;
    private String message;
    private Object data;
    private HttpStatus status = HttpStatus.OK;

    public ResponseDataMessage(String message, Object data) {
        this.message = message;
        this.data = data;
    }
}
