package com.example.AuthDemo.service.impl;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import com.example.AuthDemo.dtos.Request.UserLoginRequest;
import com.example.AuthDemo.dtos.Request.UserRegisterRequest;
import com.example.AuthDemo.dtos.Response.AuthResponse;
import com.example.AuthDemo.dtos.Response.UserResponse;
import com.example.AuthDemo.entity.User;
import com.example.AuthDemo.enums.Role;
import com.example.AuthDemo.mapper.UserMapper;
import com.example.AuthDemo.repository.UserRepository;
import com.example.AuthDemo.security.JwtService;
import com.example.AuthDemo.service.UserService;
import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final UserMapper mapper;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;

    @Transactional 
    @Override 
    public UserResponse register(UserRegisterRequest req){
        if(repository.existsByEmail(req.email())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email is already registered!");
        }
        User user = mapper.toEntity(req);
        user.setPassword(encoder.encode(req.password()));
        user.setRole(Role.USER);
        User saveUser = repository.save(user);
        return mapper.toResponse(saveUser);
    }

    @Transactional (readOnly = true) 
    @Override 
    public AuthResponse login(UserLoginRequest req){
        User user = repository.findByEmail(req.email())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT, "Invalid email or password!"));
        
        if(!encoder.matches(req.password(), user.getPassword())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password!");
        }

        UserDetails userDetails = org.springframework.security.core.userdetails.User
            .withUsername(user.getEmail())
            .password(user.getPassword())
            .roles(user.getRole().name())
            .build();
        
        String token = jwtService.generateToken(userDetails);

        return new AuthResponse(
            token, "Bearer", 3600, mapper.toResponse(user));
    }
}