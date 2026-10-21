package com.example.AuthDemo.service;
import com.example.AuthDemo.dtos.Request.UserLoginRequest;
import com.example.AuthDemo.dtos.Request.UserRegisterRequest;
import com.example.AuthDemo.dtos.Response.AuthResponse;
import com.example.AuthDemo.dtos.Response.UserResponse;

public interface UserService {
    UserResponse register(UserRegisterRequest req);
    AuthResponse login(UserLoginRequest log);
}
