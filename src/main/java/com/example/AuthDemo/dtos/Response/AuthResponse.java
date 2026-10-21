package com.example.AuthDemo.dtos.Response;

public record AuthResponse(
    String token,
    String tokenType,
    long expiresIn,
    UserResponse user
) {}
