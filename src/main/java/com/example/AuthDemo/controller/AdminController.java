package com.example.AuthDemo.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController 
@RequestMapping ("/api/admin")
public class AdminController {
    
    @GetMapping ("/me")
    public ResponseEntity<String> admin(){
        return ResponseEntity.ok("Admin is authenticated");
    }

    @GetMapping ("/dashboard")
    public ResponseEntity<String> getAdminDashboard(){
        return ResponseEntity.ok("Get admin dashboard");
    }
}