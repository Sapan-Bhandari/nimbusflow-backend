package com.sapan.job.exception;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorResponse {

    private LocalDateTime timestamp;
    private int status;
    private String error;

    public ErrorResponse(
            LocalDateTime timestamp,
            int status,
            String error) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
    }
}