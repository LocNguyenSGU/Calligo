package com.example.commonservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
@Data
@NoArgsConstructor
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
