package com.example.AuthDemo.dtos.Response;
import java.time.LocalDateTime;

public record ErrorResponse(
    LocalDateTime timestamp,
    int status,
    String error,
    String message
) {}
