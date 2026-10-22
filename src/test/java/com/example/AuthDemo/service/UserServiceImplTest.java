package com.example.AuthDemo.service;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import com.example.AuthDemo.service.impl.UserServiceImpl;

@ExtendWith (MockitoExtension.class)
class UserServiceImplTest {

    @Mock 
    private UserRepository repository;

    @Mock 
    private UserMapper mapper;

    @Mock 
    private JwtService jwtService;

    @Mock 
    private PasswordEncoder passwordEncoder;

    @InjectMocks 
    private UserServiceImpl serviceImpl;

    @Test 
    void register_shouldSaveUserWithHasedPassword(){
        String email = "student@example.com";
        String rawPassword = "MyPassword123";
        String hashedPassword = "$2a$10$mockHashedPassword";
        UserRegisterRequest request = new UserRegisterRequest(email, rawPassword, rawPassword);

        User user = new User();
        user.setEmail(email);
        user.setPassword(rawPassword);

        UserResponse expectedResponse = new UserResponse(1L, email, "USER");

        when(repository.existsByEmail(email))
            .thenReturn(false);

        when(mapper.toEntity(request))
            .thenReturn(user);

        when(passwordEncoder.encode(rawPassword))
            .thenReturn(hashedPassword);

        when(repository.save(user))
            .thenReturn(user);

        when(mapper.toResponse(user))
            .thenReturn(expectedResponse);

        UserResponse actualResponse = serviceImpl.register(request);

        assertEquals(hashedPassword, user.getPassword());
        assertEquals(Role.USER, user.getRole());
        assertEquals(expectedResponse, actualResponse);
        verify(repository).existsByEmail(email);
        verify(passwordEncoder).encode(rawPassword);
        verify(repository).save(user);
        verify(mapper).toResponse(user);
    }

    @Test 
    void register_shouldRejectDuplicatedEmail(){
        String email = "student@gmail.com";
        UserRegisterRequest request = new UserRegisterRequest(email, "Mypassword123", "Mypassword123");

        when(repository.existsByEmail(email))
            .thenReturn(true);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> serviceImpl.register(request));
        assertEquals(HttpStatus.CONFLICT, exception.getStatusCode());

        verify(repository, never()).save(org.mockito.ArgumentMatchers.any(User.class));
    }

    @Test 
    void login_shouldReturnAuthResponse_whenCredentialsAreValid(){
        String email = "student@example.com";
        String rawPassword = "MyPassword123";
        String hashedPassword = "$2a$10$mockHashedPassword";
        String token = "mock.jwt.token";

        User user = new User();
        user.setId(1L);
        user.setEmail(email);
        user.setPassword(hashedPassword);
        user.setRole(Role.USER);

        UserLoginRequest request = new UserLoginRequest(email, rawPassword);
        UserResponse response = new UserResponse(1L, email, "USER");

        when(repository.findByEmail(email))
            .thenReturn(Optional.of(user));

        when(passwordEncoder.matches(rawPassword, hashedPassword))
            .thenReturn(true);

        when(jwtService.generateToken(any(UserDetails.class)))
            .thenReturn(token);

        when(mapper.toResponse(user))
            .thenReturn(response);

        AuthResponse actualResponse = serviceImpl.login(request);

        assertEquals(token, actualResponse.token());
        assertEquals("Bearer", actualResponse.tokenType());
        assertEquals(3600, actualResponse.expiresIn());
        assertEquals(response, actualResponse.user());

        verify(repository).findByEmail(email);
        verify(passwordEncoder).matches(rawPassword, hashedPassword);
        verify(jwtService).generateToken(any(
            UserDetails.class
        ));
    }

    @Test 
    void login_shouldThrowUnauthorized_whenPasswordIsIncorrect(){
        String email = "student@example.com";
        String rawPassword = "WrongPassword123!";
        String hashedPassword = "$2a$10$mockHashedPassword";

        User user = new User();
        user.setId(1L);
        user.setEmail(email);
        user.setPassword(hashedPassword);
        user.setRole(Role.USER);

        UserLoginRequest request = new UserLoginRequest(email, rawPassword);

        when(repository.findByEmail(email))
            .thenReturn(Optional.of(user));

        when(passwordEncoder.matches(rawPassword, hashedPassword))
            .thenReturn(false);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> serviceImpl.login(request));

        assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatusCode());

        verify(jwtService, never())
            .generateToken(any(UserDetails.class));
    }
}