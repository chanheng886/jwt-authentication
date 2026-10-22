package com.example.AuthDemo.controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.AuthDemo.dtos.Request.UserLoginRequest;
import com.example.AuthDemo.dtos.Request.UserRegisterRequest;
import com.example.AuthDemo.dtos.Response.AuthResponse;
import com.example.AuthDemo.dtos.Response.UserResponse;
import com.example.AuthDemo.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping ("/api/auth")
@RestController 
@RequiredArgsConstructor 
public class AuthController {
    private final UserService service;

    @PostMapping ("/register")
    public ResponseEntity<UserResponse> userRegister(@Valid @RequestBody UserRegisterRequest req){
        UserResponse res = service.register(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @PostMapping ("/login")
    public ResponseEntity<AuthResponse> userLogin(@Valid @RequestBody UserLoginRequest req){
        AuthResponse res = service.login(req);
        return ResponseEntity.ok(res);
    }

    // Testing endpoint
    // @GetMapping ("/me")
    // public ResponseEntity<String> me(){
    //     return ResponseEntity.ok("You are authenticated!");
    // }
}