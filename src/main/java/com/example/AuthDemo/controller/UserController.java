package com.example.AuthDemo.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController 
@RequestMapping ("/api/users")
public class UserController {

    @GetMapping ("/me") 
    public ResponseEntity<String> getCurrentUser(){
        return ResponseEntity.ok("You are authenticated");
    }
}