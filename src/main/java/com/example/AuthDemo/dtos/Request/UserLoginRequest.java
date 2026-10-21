package com.example.AuthDemo.dtos.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserLoginRequest(
    @NotBlank (message = "Email is required!")
    @Email (message = "Invalid email, please try again")
    String email,
    @NotBlank (message = "Password is required~")
    String password
) {}
