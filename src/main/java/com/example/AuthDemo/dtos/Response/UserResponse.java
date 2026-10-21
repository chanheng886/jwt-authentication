package com.example.AuthDemo.dtos.Response;

public record UserResponse(
    Long id,
    String email,
    String role
) {}
