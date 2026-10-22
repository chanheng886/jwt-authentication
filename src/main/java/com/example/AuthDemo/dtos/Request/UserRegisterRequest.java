package com.example.AuthDemo.dtos.Request;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserRegisterRequest(
    @NotBlank (message = "Email is required!")
    @Email (message = "Invalid email, please try again!")
    String email,
    @NotBlank (message = "Password is required!")
    String password,
    @NotBlank (message = "Please confirm your password!")
    String confirmPassword
) {
    @AssertTrue (message = "Password and confirm password do not match!")
    public boolean isPasswordMatched(){
        return password != null && password.equals(confirmPassword);
    }
}
