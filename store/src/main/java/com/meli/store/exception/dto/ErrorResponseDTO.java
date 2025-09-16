package com.meli.store.exception.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
public class ErrorResponseDTO {

    private String code;
    private String message;
    private int status;
    private LocalDateTime timestamp;
    private Map<String, String> validationErrors;

    public ErrorResponseDTO(String code, String message, int status) {
        this.code = code;
        this.message = message;
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }

}
